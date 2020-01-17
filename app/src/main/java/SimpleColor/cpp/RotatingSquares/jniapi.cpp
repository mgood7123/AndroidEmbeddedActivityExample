//
// Copyright 2011 Tero Saarni
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

#include <cstdint>
#include <jni.h>
#include <android/native_window.h> // requires ndk r5 or newer
#include <android/native_window_jni.h> // requires ndk r5 or newer

#include "logger.h"
#include "renderer.h"

#define LOG_TAG "EglSample"

class Instance {
public:
    ANativeWindow *window = nullptr;
    Renderer *renderer = nullptr;
};

extern "C"
JNIEXPORT jlong JNICALL
Java_SimpleColor_NativeView_nativeNewInstance(JNIEnv *env, jobject clazz) {
    LOG_INFO("Java_SimpleColor_NativeView_nativeNewInstance");
    Instance * instance = new Instance;
    LOG_INFO("Got instance: %p", instance);
    return reinterpret_cast<jlong>(instance);
}

extern "C"
JNIEXPORT void JNICALL
Java_SimpleColor_NativeView_nativeOnCreate(JNIEnv* jenv, jobject clazz, jlong instance) {
    LOG_INFO("Java_SimpleColor_NativeView_nativeOnCreate");
    if (instance != 0) reinterpret_cast<Instance*>(instance)->renderer = new Renderer();
}

extern "C"
JNIEXPORT void JNICALL
Java_SimpleColor_NativeView_nativeOnDestroy(JNIEnv* jenv, jobject clazz, jlong instance) {
    LOG_INFO("Java_SimpleColor_NativeView_nativeOnDestroy");
    delete reinterpret_cast<Instance*>(instance)->renderer;
    reinterpret_cast<Instance*>(instance)->renderer = nullptr;
}

extern "C"
JNIEXPORT void JNICALL
Java_SimpleColor_NativeView_nativeDeleteInstance(JNIEnv *env, jobject clazz, jlong instance) {
    LOG_INFO("Java_SimpleColor_NativeView_nativeDeleteInstance");
    delete reinterpret_cast<Instance*>(instance);
    instance = 0;
}

extern "C"
JNIEXPORT void JNICALL
Java_SimpleColor_NativeView_nativeSetSurface(JNIEnv* jenv, jobject clazz, jlong instance, jobject surface) {
    LOG_INFO("Java_SimpleColor_NativeView_nativeSetSurface");
    LOG_INFO("surface = %p", surface);
    Instance * mInstance = reinterpret_cast<Instance*>(instance);
    if (surface != nullptr) {
        mInstance->window = ANativeWindow_fromSurface(jenv, surface);
        LOG_INFO("Got window %p", mInstance->window);
    } else {
        LOG_INFO("Releasing window");
        ANativeWindow_release(mInstance->window);
        mInstance->window = nullptr;
    }
}