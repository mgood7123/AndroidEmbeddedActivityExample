cmake_minimum_required(VERSION 3.4.1)

add_library(SimpleColor SHARED jniapi.cpp renderer.cpp)
target_link_libraries(SimpleColor android log EGL GLESv1_CM)