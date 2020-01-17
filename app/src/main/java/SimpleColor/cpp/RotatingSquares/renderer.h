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

#ifndef RENDERER_H
#define RENDERER_H

#include <pthread.h>
#include <EGL/egl.h> // requires ndk r5 or newer
#include <GLES/gl.h>

class Renderer {

public:
    Renderer();
    virtual ~Renderer();

private:

    // android window, supported by NDK r5 and newer
    ANativeWindow* _window{};

    EGLDisplay _display;
    EGLSurface _surface;
    EGLContext _context;
    GLfloat _angle;

    bool initialize();
    void destroy();
};

#endif // RENDERER_H
