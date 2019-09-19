package com.surfacetech.orderitonline;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.surfacetech.orderitonline.Common.Common;
import com.surfacetech.orderitonline.Retrofit.IMRestaurantAPI;
import com.surfacetech.orderitonline.Retrofit.RetrofitClient;


import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SplashScreen extends AppCompatActivity {

    IMRestaurantAPI myRestuarantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AlertDialog dialog;


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {



                        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                            @Override
                            public void onSuccess(Account account) {

                                dialog.show();
                                //Toast.makeText(SplashScreen.this, "Already Signed", Toast.LENGTH_SHORT).show();
                                compositeDisposable.add(myRestuarantAPI.getUser(Common.API_KEY,account.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(userModel -> {

                                    if (userModel.isSuccess()) {
                                            Common.currentUser = userModel.getResult().get(0);
                                            Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();

                                    } else
                                    {
                                        Intent intent = new Intent(SplashScreen.this, UpdateInfoActivity .class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    dialog.dismiss();
                                        },
                                        throwable -> {
                                    dialog.dismiss();
                                            Toast.makeText(SplashScreen.this, "GET USER API"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }));
                            }

                            @Override
                            public void onError(AccountKitError accountKitError) {
                                Toast.makeText(SplashScreen.this, "Please  Sign in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(SplashScreen.this, "you have to accept the permission to usse this app", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

    }

    private void init() {
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myRestuarantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMRestaurantAPI.class);

    }

    //private void printKeyHash() {
    //        try{
    //            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),
    //                    PackageManager.GET_SIGNATURES);
    //            for (Signature signature:info.signatures)
    //            {
    //                MessageDigest md = MessageDigest.getInstance("SHA");
    //                md.update(signature.toByteArray());
    //                Log.d("KEY_HASH", Base64.encodeToString(md.digest(),Base64.DEFAULT));
    //            }
    //        } catch (PackageManager.NameNotFoundException e) {
    //            e.printStackTrace();
    //        } catch (NoSuchAlgorithmException e) {
    //            e.printStackTrace();
    //        }
    //    }
}
