package com.example.mychatapp_finalproject.encryption;


import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

/**
 * EncryptionUtil to encrypt data in sharedPreferences
 * Reference the following for details:
 * <a href="https://developer.android.com/reference/androidx/security/crypto/package-summary">Android docs for Crypto Library</a>
 * <a href="https://stackoverflow.com/questions/62498977/how-to-create-masterkey-after-masterkeys-deprecated-in-android">Implementation for deprecated MasterKeys</a>.
 */
public class EncryptionUtil {
    private static final String TAG = "EncryptionUtil";
    public static String USER_ID_KEY = "user_id_key";

    public static void storeEncryptedString(Context context, String key, String string) {
        SharedPreferences sharedPreferences = EncryptionUtil.
                getEncryptedSharedPreferences(context);
        assert sharedPreferences != null;
        Log.d(TAG, "Successfully stored encrypted string");
        sharedPreferences.edit().putString(key, string).apply();
    }

    public static String retrieveEncryptedString(Context context, String key) {
        SharedPreferences sharedPreferences = EncryptionUtil.
                getEncryptedSharedPreferences(context);
        assert sharedPreferences != null;
        Log.d(TAG, "Successfully retrieved encrypted string");
        return sharedPreferences.getString(key, "");
    }

    private static SharedPreferences getEncryptedSharedPreferences(Context context)  {
        try {
            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                    "master_key",
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).build();
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyGenParameterSpec(spec)
                    .build();

            Log.d(TAG, "Successfully generated encrypted shared preferences");
            return EncryptedSharedPreferences.create(context,
                    "encrypted_preferences",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
