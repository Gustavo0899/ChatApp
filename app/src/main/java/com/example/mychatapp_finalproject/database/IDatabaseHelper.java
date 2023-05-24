package com.example.mychatapp_finalproject.database;

import com.example.mychatapp_finalproject.model.Model;
import java.util.ArrayList;

/**
 * IDatabaseHelper is a wrapper of any specific implementation of a database
 */
public interface IDatabaseHelper {
    <T> void create(String id, Model model, T data);
    <T> void update(String id, Model model, T data);
    void delete(String id, Model model);

    /**
     * Method to only get data if you don't need to use a model's methods
     */
    void getDataOfModel(String id, Model model, ICallback callback);
    void getDataOfAllOfModel(Model model, ICallback callback);

    /**
     * Method to get the model and use its methods
     */
    <T> void getModel(String id, Model model, Class<T> clazz, ICallback callback);
    <T> void getAllOfModel(Model model, Class<T> clazz, ICallback callback);
    void getModelWithSameEmail(String email, Model model, ICallback callback);
    void getAllContactsOfUser(ArrayList<String> modelsId, Model model, ICallback callback);
}
