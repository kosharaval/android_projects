from flask import Flask, render_template,  request, escape
from flask_socketio import SocketIO, emit
from flask_pymongo import PyMongo
from bson.json_util import dumps, loads 
from dbroute import *

app = Flask(__name__)
app.config['SECRET_KEY'] = 'someSecretKey123'
app.config['DEBUG'] = True

socketio = SocketIO(app)
users = {}

app.config["MONGO_URI"] = "mongodb+srv://ravalk:pass@cluster0-34sac.mongodb.net/PocketBook?retryWrites=true&w=majority" # replace the URI with your own connection
mongo = PyMongo(app)

@socketio.on('addUser')
def retrieveUser(fname,lname,email,phone,password,budget):
    fname = fname
    lname = lname
    email = email
    phone = phone
    budget = budget
    password = password
    register = addUser(fname,lname,email,phone,password,budget)
    userInfo = list(getUserData(email,password))
    json_data = dumps(userInfo)
    if register:
       emit('userAdded', "User Added Successfully", json_data)
    else:
       emit('userAdded', "User Added Failed") 
       
@socketio.on('addBudget')
def updateUserData(email,budget):
    email = email
    budget = budget
    update = updateUser(email,budget)
    if update:
        emit('userUpdate',"Update Successfully")
    else:
        emit('userUpdate', "Update Failed")

   #getting data for transactions and category 
@socketio.on('addTransaction')
def retrieveTransaction(category_name,category_type,amount,description,date,userEmail):
    category_name = category_name
    category_type = category_type
    amount = amount
    description = description
    date = date
    userEmail = userEmail
    add = addTransaction(category_name,category_type,amount,description,date,userEmail)
    if add:
       emit('addedNewTransaction', "Transaction Added Successfully")
    else:
       emit('addedNewTransaction', "Transaction Addition Failed") 
       

@socketio.on('getUserData')
def retrieveDataUser(email,password):
    print("Getting data from database. Request from {}".format(email))
    userList = list(getUserData(email,password))
    print(userList)
    json_data = dumps(userList)
    if userList:
       emit('userData', json_data)
    else:
       emit('userData', "Transaction Addition Failed") 

@socketio.on('getTransactionData')
def retrieveDataTransaction(email):
     print("Getting data from database. Request from {}".format(email))
     transactionlist = list(getTransaction(email))
     print(transactionlist)
     json_data = dumps(transactionlist)
     emit('datasent', json_data)


@socketio.on('getCategory')
def retrieveCategory():
    categorylist= list(getCategoryData())
    print(categorylist)
    json_data = dumps(categorylist)
    emit('datasent', json_data)


# this would send a message to ALL clients
def send_broadcast_message(msg):
    emit('notification', msg, broadcast=True)
    

if __name__ == '__main__':
    socketio.run(app,host = '0.0.0.0')  # here, we are using socketio instaead of app because it has more features
