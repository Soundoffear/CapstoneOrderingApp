package com.example.soundoffear.capstoneorderingapp.utilities;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PointsSystemClass {

    public static String getAllPoints(String userID) {
        final String[] pointsValue = {null};

        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_USERS).child(userID).child(Constants.DATABASE_USER_POINTS);

        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!TextUtils.isEmpty(String.valueOf(dataSnapshot.getValue()))) {
                    pointsValue[0] = (String.valueOf(dataSnapshot.getValue()));
                } else {
                    pointsValue[0] = "0";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return pointsValue[0];
    }

    public static void addPoints(final String addPointsValue, String userID) {
        final DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_USERS).child(userID).child(Constants.DATABASE_USER_POINTS);

        Log.d("TOTAL PRICE SENT", addPointsValue);

        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!TextUtils.isEmpty(String.valueOf(dataSnapshot.getValue()))) {
                    String currentPoint;
                    Log.d("CURRENT VALUE" , String.valueOf(dataSnapshot.getValue()));
                    if(dataSnapshot.getValue() == null) {
                        currentPoint = "0";
                        Log.d("FOR NULL", " " + currentPoint);
                    } else {
                        currentPoint = String.valueOf(dataSnapshot.getValue());

                        Log.d("FOR NOT NULL", " " + currentPoint);
                    }
                    double pointsCurrent = Double.parseDouble(currentPoint);
                    double pointsToAdd = Double.parseDouble(addPointsValue);
                    double finalPoints = pointsCurrent + pointsToAdd;
                    finalPoints = Math.round(finalPoints);
                    Log.d("FINAL", pointsCurrent + "+" + pointsToAdd + "=" + finalPoints);
                    dRef.setValue(String.valueOf(finalPoints));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("Points Updated", "Points added: " + addPointsValue);
    }

    public static void removePoints(final String pointsToRemove, String userID) {
        final DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_USERS).child(userID).child(Constants.DATABASE_USER_POINTS);

        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentPoint = String.valueOf(dataSnapshot.getValue());
                double pointsCurrent = Double.parseDouble(currentPoint);
                double pointsToAdd = Double.parseDouble(pointsToRemove);
                double finalPoints = pointsCurrent - pointsToAdd;
                finalPoints = Math.round(finalPoints);
                dRef.setValue(String.valueOf(finalPoints));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("Points Updated", "Points removed: " + pointsToRemove);
    }

}
