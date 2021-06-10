# Desarrollo del programa
Para el desarollo de este programa primero se creo el metodo para generar la clave de 128 bits usando el algoritmo PBKDF2, tambien se realizo un metodo para generar el hash SHA-1, para que la clave no se guarde en texto plano, haciendo uso de la clase de java.security, MessageDigest, luego para poder cifrar un archivo, un metodo que recibe como paramentros un archivo y una clave, entonces por medio de javax.crypto.Cipher se declara el algoritmo de cifrado y se la pasa la clave luego se escribe el mensaje cifrado en un archivo despues se genera y se guarda en un archivo  el hash sha1.

# Dificultades
La mayor dificultad que presente fue a la hora de generar el algoritmo de hash SHA-1 puesto que no entendia bien como podria hacerlo.

# Conclusiones
Con este programa se pude cifrar y decifrar archivos con el algoritmo AES y una clave de 128 bits, empleada con el algoritmo PBKDF2. Tambien se pude apreciar el proceso que se emplea para la criptografía y así lograr tranformar un mensaje, de tal forma que quede incomprensible y de esta forma la información se mantendra segura.
