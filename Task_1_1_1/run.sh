javac src/main/java/org/example/HeapSort.java -d ./build
javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages org.example
java -cp ./build org.example.HeapSort