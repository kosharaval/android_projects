"""
   https://account.mongodb.com/account/login
   
   sudo pip3 install Flask-WTF
"""
from flask import Flask, render_template,  request, escape
import pymongo
import json
import urllib

# config system
app = Flask(__name__)

dbconnection = pymongo.MongoClient("mongodb+srv://ravalk:pass@cluster0.a4bpt.mongodb.net/")
db = dbconnection["PocketBook"]

# User Table CRUD Operations
def addUser(fname,lname,email,phone,password,budget):
    user = {'fname':fname, 'lname':lname, 'email':email, 'phone':phone, 'password':password,"budget":budget}
    result = db.user.insert_one(user)
    if result:
        return "true"
    else:
        return "false"

def getUserData(email, password):
    user = db.user.find({"email":email, "password": password})
    return user

def updateUser(email,budget):
    user = db.user.update_one(
        {'email': email},
        {'$set':
            {'budget':budget}
        })
    return user

def addTransaction(category_name,category_type,amount,description,date,userEmail):
    transaction={'category_name':category_name,'category_type':category_type,'amount': amount,'description':description,'date':date,'userEmail':userEmail}
    result=db.transaction.insert_one(transaction)
    if result:
        return "true"
    else:
        return "false"


def getTransaction(userEmail):
    transaction=db.transaction.find({'userEmail':userEmail})
    return transaction

def getCategoryData():
    category=db.category.find({})
    return category




if __name__ == '__main__':
    app.run(host='0.0.0.0',debug=True)
