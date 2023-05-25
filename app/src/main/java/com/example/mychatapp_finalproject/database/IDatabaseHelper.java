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
    <T> void getModel(String id, Model model, Class<T> clazz, ICallback callback);
    <T> void getAllOfModel(Model model, Class<T> clazz, ICallback callback);
    void getUserWithSameEmail(String email, Model model, ICallback callback);
    void getAllContactsOfUser(ArrayList<String> modelsId, Model model, ICallback callback);
    void getAllMessagesOfChat(ArrayList<String> modelsId, Model model, ICallback callback);
}
