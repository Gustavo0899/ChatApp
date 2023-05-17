package com.example.mychatapp_finalproject.database;

/**
 * ICallback receives general asynchronous callback responses from Databases. For more information on callbacks:
 * <a href="https://stackoverflow.com/questions/57330766/why-does-my-function-that-calls-an-api-or-launches-a-coroutine-return-an-empty-o">
 *     Source 1
 * </a>
 * <a href="https://stackoverflow.com/questions/50650224/wait-until-firestore-data-is-retrieved-to-launch-an-activity">
 *     Source 2
 * </a>
 */

public interface ICallback {
    <T> void onCallback(T callback);
}
