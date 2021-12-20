var iv = "F27D5C9927726BCEFE7510B1BDD3D137";
var salt = "3FF2EC019C627B945225DEBAD71A01B6985FE84C95A70EB132882F88C0A59A55";
var plainText = "Password";
var keySize = 128;
var iterations = iterationCount = 10000;
var passPhrase = "Password";
var cipherText = "/AFfJyswH99yaf/Zr8aTr9gmnhkskf2VGW1iYe14nLzoF9SFF1u4b3N3ebZeiS7av12Q7D0"
  + "71BpaWVek7qBZ4Q+OaUbzIAVYswvi/y460ImSsEkI4f3eZeleA1EULjSbPqYT0cZyHy6B"
  + "+BO7z1FMZwqxDGfPZaO7TgPOpKsIp34FIzKxBk+2YjRpf3cOVCyMY/qRQC3nxuEbBYr8y"
  + "IBu0tBuN1vRxfUD9rsFkf0/CszQrnRh2UqfIzI5XU5dgkifoab4b2Qa6/O78+GcbwuUo+"
  + "LKYC2KrVsVU6YzFV9I9eLjonIShGe2w27BedxpCk/G5pBxQIKCoFna79TKflSQr8O1vYq"
  + "taha41BzHw1h1WaRDFOv8NO0sgn5uEsmMsWuw5EDqwDdbJ99VFKYPF3Rh8mNcwbIreUID"
  + "B6zGfecU9FPacGlGuYM7rxFMut6HMiDrXwsmYQkL5wQR8yoF7j78U6RJPuUosrYZEO/XG"
  + "5TMFUIPvuU/jP/bvUHk6INOP31RFNI6cG6qWrRtwCoGztRbC8GENA5zgnF95/c1I65ZCT"
  + "MJ6VB4cxD0eXMTW3Ky5qAtTv4B0Z0bn1hKFGxmlBnJF50JZ1ZGTaF2vTRTTUJTFo62v5Z"
  + "0bSzH45sjMd6QOZdATn+3KY8ekXJY8N7WCQvFZMCZWVaHqfspFBfYzaZWzB6SuwioUN5z"
  + "r5KCrLuLCUHJ6I+zT4Pj+Baa19YtkbQhMNcky0936/dDHYTvTSu/of4Jux7ze30+tFqzXsWZOGsABdV0byv+6q8XptJne61A/w==";
function encrypt(password){
	var aesUtil = new AesUtil(keySize, iterationCount)
    var encrypted = aesUtil.encrypt(salt, iv, passPhrase, password);
    return encrypted;
}

function decrypt(passwordEncrypt){	
	var aesUtil = new AesUtil(keySize, iterationCount)
    var decrypted = aesUtil.decrypt(salt, iv, passPhrase, passwordEncrypt);
    return decrypted;
}

