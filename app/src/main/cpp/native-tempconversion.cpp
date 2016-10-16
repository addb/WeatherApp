//
// Created by Arjun on 10/15/2016.
//

#include "native-tempconversion.h"
#include <string.h>
#include <stdio.h>
#include <jni.h>


extern "C"{
    JNIEXPORT jstring JNICALL Java_com_addb_weather_MainActivity_hello( JNIEnv* env, jobject obj ){
        return env->NewStringUTF( "Hello from JNI !");

    }
    JNIEXPORT jfloatArray JNICALL Java_com_addb_weather_MainActivity_change(JNIEnv* env, jobject obj, jfloatArray arr){
        int n;
    }

    JNIEXPORT jfloat JNICALL Java_com_addb_weather_MainActivity_changeToFarenheit(JNIEnv* env, jobject obj, jfloat n){
        n = (n * 9/5.0) +32;
        return n;
    }
    JNIEXPORT jfloat JNICALL Java_com_addb_weather_MainActivity_changeToCelsius(JNIEnv* env, jobject obj, jfloat n){
        n = (n-32) * 5/9.0;
        return n;
    }
}