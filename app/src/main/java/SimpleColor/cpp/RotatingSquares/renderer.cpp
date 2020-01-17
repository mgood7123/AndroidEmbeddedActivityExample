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

#include "GLIS_NANO.h"
#include <cstdint>
#include <unistd.h>
#include <pthread.h>
#include <android/native_window.h> // requires ndk r5 or newer
#include <EGL/egl.h> // requires ndk r5 or newer
#include <GLES/gl.h>

#include "logger.h"
#include "renderer.h"

#define LOG_TAG "EglSample"

Renderer::Renderer() : _display(nullptr), _surface(nullptr), _context(nullptr), _angle(0) {
    LOG_INFO("Renderer instance created");
}

Renderer::~Renderer() {
    LOG_INFO("Renderer instance destroyed");
}

bool Renderer::initialize() {
    const EGLint attribs[] = {
            EGL_SURFACE_TYPE, EGL_WINDOW_BIT,
            EGL_BLUE_SIZE, 8,
            EGL_GREEN_SIZE, 8,
            EGL_RED_SIZE, 8,
            EGL_NONE
    };
    EGLDisplay display;
    EGLConfig config;
    EGLint numConfigs;
    EGLint format;
    EGLSurface surface;
    EGLContext context;
    EGLint width;
    EGLint height;
    GLfloat ratio;

    LOG_INFO("Initializing context");

    display = GLIS_error_to_string_exec_EGL(eglGetDisplay(EGL_DEFAULT_DISPLAY));
    if (display == EGL_NO_DISPLAY) {
        destroy();
        return false;
    }

    EGLBoolean r = GLIS_error_to_string_exec_EGL(eglInitialize(display, nullptr, nullptr));
    if (r == EGL_FALSE) {
        destroy();
        return false;
    }

    display = GLIS_error_to_string_exec_EGL(eglGetDisplay(EGL_DEFAULT_DISPLAY));
    if (display == EGL_NO_DISPLAY) {
        destroy();
        return false;
    }

    r = GLIS_error_to_string_exec_EGL(eglChooseConfig(display, attribs, &config, 1, &numConfigs));
    if (r == EGL_FALSE) {
        destroy();
        return false;
    }

    r = GLIS_error_to_string_exec_EGL(eglGetConfigAttrib(display, config, EGL_NATIVE_VISUAL_ID, &format));
    if (r == EGL_FALSE) {
        destroy();
        return false;
    }

    ANativeWindow_setBuffersGeometry(_window, 0, 0, format);

    surface = GLIS_error_to_string_exec_EGL(eglCreateWindowSurface(display, config, _window, nullptr));
    if (surface == EGL_NO_SURFACE) {
        destroy();
        return false;
    }

    context = GLIS_error_to_string_exec_EGL(eglCreateContext(display, config, nullptr, nullptr));
    if (context == EGL_NO_CONTEXT) {
        destroy();
        return false;
    }

    r = GLIS_error_to_string_exec_EGL(eglMakeCurrent(display, surface, surface, context));
    if (r == EGL_FALSE) {
        destroy();
        return false;
    }

    EGLBoolean r1 = GLIS_error_to_string_exec_EGL(eglQuerySurface(display, surface, EGL_WIDTH, &width));
    if (r1 == EGL_FALSE) {
        destroy();
        return false;
    }
    EGLBoolean r2 = GLIS_error_to_string_exec_EGL(eglQuerySurface(display, surface, EGL_HEIGHT, &height));
    if (r2 == EGL_FALSE) {
        destroy();
        return false;
    }

    _display = display;
    _surface = surface;
    _context = context;

    glDisable(GL_DITHER);
    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_FASTEST);
    glClearColor(0, 0, 0, 0);
    glEnable(GL_CULL_FACE);
    glShadeModel(GL_SMOOTH);
    glEnable(GL_DEPTH_TEST);

    glViewport(0, 0, width, height);

    ratio = (GLfloat) width / height;
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glFrustumf(-ratio, ratio, -1, 1, 1, 10);

    return true;
}

void Renderer::destroy() {
    LOG_INFO("Destroying context");

    eglMakeCurrent(_display, EGL_NO_SURFACE, EGL_NO_SURFACE, EGL_NO_CONTEXT);
    eglDestroyContext(_display, _context);
    eglDestroySurface(_display, _surface);
    eglTerminate(_display);

    _display = EGL_NO_DISPLAY;
    _surface = EGL_NO_SURFACE;
    _context = EGL_NO_CONTEXT;
}