@ECHO OFF
rem Build

javac -cp src -d class src\br\com\mvbos\emundos\Window.java

jar cfm EntreMundos.jar META-INF\MANIFEST.MF -C class/ .