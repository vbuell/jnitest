cmake_minimum_required(VERSION 2.8)

project(fireapex)

find_package(JNI)
include_directories(${JNI_INCLUDE_DIRS})

add_definitions(-std=c99)

add_library(fireapex SHARED
  testPin.c
)

install(TARGETS fireapex
  LIBRARY DESTINATION lib
)
