cmake_minimum_required(VERSION 3.4.1)

add_library(cube SHARED jniapi.cpp renderer.cpp)
target_link_libraries(cube android log EGL GLESv1_CM)