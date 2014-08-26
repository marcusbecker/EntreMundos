@ECHO OFF
rem Build

mkdir class

javac -cp src -d class src\br\com\mvbos\emundos\Window.java

jar cfm EntreMundos.jar META-INF\MANIFEST.MF -C class/ .