package com.example.finalprojectrecipefinderappritika;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FireStoreManager {

    interface  FireStoreManagerInterfaceListener{
        void finishFireStoreWithRecipeList(ArrayList<Recipe> recipesList);

        void finishFireStoreWithRecipe(Recipe r);
    }
    FirebaseFirestore firestore;
    FireStoreManagerInterfaceListener listener;

    //FirebaseStorage firestorage;

    public FireStoreManager(){
        if (firestore==null) {
            firestore = FirebaseFirestore.getInstance();
        }
//        if(firestorage==null){
//            firestorage = FirebaseStorage.getInstance();
//        }
    }

    void getAllRecipesFromFireStoreInBGThread(){

        MyApp.executorService.execute(new Runnable() {
            ArrayList<Recipe> recipesList = new ArrayList<>();
            @Override
            public void run() {
                firestore.collection("Recipes")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot document:task.getResult()) {
                                        int id = (int) (long)document.get("recipeID");
                                        Recipe r = new Recipe(id,(String)document.get("recipeName"));
                                        r.setDocumentID(document.getId());
//                                        //convert recipeImage to bitmap to store
                                       Bitmap img = fromBase64toBitMap((String) document.get("recipeImage"));
                                       r.setRecipeImage(img);
                                        recipesList.add(r);
                                    }
                                    MyApp.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            listener.finishFireStoreWithRecipeList(recipesList);
                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }
    
    void getOneRecipeFromFireStoreinBGThreadWith(int recipeID){
        
        MyApp.executorService.execute(new Runnable() {
            Recipe r;
            @Override
            public void run() {
                firestore.collection("Recipes").whereEqualTo("recipeID",(long)recipeID)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                              if(task.isSuccessful()){
                                  for (QueryDocumentSnapshot document:task.getResult()) {
                                      r = new Recipe(recipeID,(String) document.get("recipeName"));
                                      r.setRecipeDescription((String)document.get("recipeDescription"));
                                      r.setNutrition((ArrayList<String>) document.get("nutirition"));
                                      r.setCuisine((String)document.get("cuisine"));
                                      r.setCategory((String)document.get("category"));
                                      r.setDirections((ArrayList<String>) document.get("direction"));
                                      r.setTime((String)document.get("time"));
                                      r.setIngredients((ArrayList<String>) document.get("ingredients"));
                                      r.setDocumentID(document.getId());
                                      //convert recipeImage to bitmap to store
                                      Bitmap img = fromBase64toBitMap((String) document.get("recipeImage"));
                                      r.setRecipeImage(img);
                                  }
                                  MyApp.handler.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          listener.finishFireStoreWithRecipe(r);
                                      }
                                  });
                              }
                            }
                        });
            }
        });
    }

    void deleteRecipeFromFireStoreInBGThread(Recipe toBeDeleted){
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                String id =  toBeDeleted.getDocumentID();
                DocumentReference docRef = firestore.collection("Recipes").document(id);
                docRef.delete();
            }
        });
    }
    public Bitmap fromBase64toBitMap(String imageString){
        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        //image.setImageBitmap(decodedImage);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public static String fromBitmapToBase64(Bitmap image)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b,Base64.DEFAULT);
    }
}
