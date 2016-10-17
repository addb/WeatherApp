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

//following function take float array containing list of temperatures and returns the float array
    JNIEXPORT jfloatArray JNICALL Java_com_addb_weather_MainActivity_convertToFahrenheit(JNIEnv* env, jobject ob, jfloatArray farr){

        jsize len = (*env).GetArrayLength(farr);
        jfloat result[len];
        jfloatArray res;
        res = env->NewFloatArray(len);
        jfloat* flt1 = env->GetFloatArrayElements( farr,0);
        for(int i=0; i<len; i++){

            result[i]= (flt1[i]* 9/5) + 32;
        }

        env->SetFloatArrayRegion(res, 0, len, result);
        return res;
    }
    JNIEXPORT jfloatArray JNICALL Java_com_addb_weather_MainActivity_convertToCelsius(JNIEnv* env, jobject ob, jfloatArray farr){

        jsize len = (*env).GetArrayLength(farr);
        jfloat result[len];
        jfloatArray res;
        res = env->NewFloatArray(len);
        jfloat* flt1 = env->GetFloatArrayElements( farr,0);
        for(int i=0; i<len; i++){

            result[i]= (flt1[i] -32) * 5/9;
        }

        env->SetFloatArrayRegion(res, 0, len, result);
        return res;
    }


//following two functions are used on single value
    JNIEXPORT jfloat JNICALL Java_com_addb_weather_MainActivity_changeToFahrenheit(JNIEnv* env, jobject obj, jfloat n){
        n = (n * 9/5) +32;
        return n;
    }
    JNIEXPORT jfloat JNICALL Java_com_addb_weather_MainActivity_changeToCelsius(JNIEnv* env, jobject obj, jfloat n){
        n = (n-32) * 5/9;
        return n;
    }
}