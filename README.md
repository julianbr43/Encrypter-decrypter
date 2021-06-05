# Encrypter-decrypter
This program has two options: 
1) Encrypt file: it receives any file as input, and a password. From the password, it generates a 128-bit key, using the PBKDF2 algorithm. Finally, the file is encrypted with the AES algorithm, using the obtained key; the result is written to another file, which also contains the SHA-1 hash of the unencrypted file. 

2) Decryption:
receives as input an encrypted file and password. The program will decrypt the file and write the result to a new file. Then it will compute the SHA-1 hash of the decrypted file and compare it to the stored hash with the encrypted file.
