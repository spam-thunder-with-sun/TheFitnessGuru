#!/usr/bin/python
import mysql.connector
from mysql.connector import errorcode

print("Hello world!")
try:      
    #mydb_1 = mysql.connector.connect(user='2YLoomalqj',  password='8lF7C5A1MT', host='remotemysql.com',  database='2YLoomalqj')
    mydb = mysql.connector.connect(user='marypoppins', 
        password='SLdZUQCvGNQ2KJq', 
        host='db4free.net', 
        database='thefitnessguru')     
except mysql.connector.Error as err:
  if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
    print("Something is wrong with your user name or password")
  elif err.errno == errorcode.ER_BAD_DB_ERROR:
    print("Database does not exist")
  else:
    print(err)

mycursor = mydb.cursor()
mycursor.execute("SELECT * FROM USERS")
myresult = mycursor.fetchall()
for x in myresult:
  print(x)

#print(mydb)