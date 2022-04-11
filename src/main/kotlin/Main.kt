


import controllers.milkAPI

import models.proteinMilk
import mu.KotlinLogging
import persistence.JSONSerializer

import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.isValidListIndex.isValidListIndexs
import utils.isValidPrice.isValidPrice
import utils.isValidProteinAmount
import utils.isValidProteinAmount.isValidProteinAmount

import java.io.File

private val logger = KotlinLogging.logger {}
//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
//private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))
private val milkapi = milkAPI(JSONSerializer(File("milks.json")))
//private val milkapi = milkAPI()


fun main(args: Array<String>) {
    runMenu()
}

fun runMenu() {
    do {
        val option = mainMenu()

        when (option) {
            1  -> addMilk()
            2  -> listMilks()
            3  -> updateMilk()
            4  -> deleteMilk()
          //  5 -> archiveMilk()
            6 -> load()
            7 -> save()
            0  -> exitApp()
            else -> println("Invalid  option entered: $option")
        }
    } while (true)
}




fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
  _____              _         _        
 |  __ \            | |       (_)       
 | |__) |_ __  ___  | |_  ___  _  _ __  
 |  ___/| '__|/ _ \ | __|/ _ \| || '_ \ 
 | |    | |  | (_) || |_|  __/| || | | |
 |_|    |_|   \___/  \__|\___||_||_| |_|
                                               
         > ----------------------------------
         > |                                |
         > |   1) Add protein milk          |
         > |   2) List all milks methods    |
         > |   3) Update a milk             |
         > |   4) Delete a milk             |
         > |   5) Archive a note            |
         > |   6) Load Note                 |  
         > |   7) Save note                |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))


}

fun listMilks() {
   // if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(""" 
         > ----------------------------------
        >   _       _       _     __  __  _  _  _    
        >  | |     (_)     | |   |  \/  |(_)| || |   
        >  | |      _  ___ | |_  | \  / | _ | || | __
        >  | |     | |/ __|| __| | |\/| || || || |/ /
        >  | |____ | |\__ \| |_  | |  | || || ||   < 
        >  |______||_||___/ \__| |_|  |_||_||_||_|\_\
                                        
                                                      
         > ----------------------------------
         > |   1) List all milks            |
         > |   2) List active notes         |
         > |   3) List archived note        |
         > |   4) List note starting with   |
         > |   5) Search Note by title      |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
        when(option){
            1-> listAllMilks()
          //  2 -> searchByProtein()
          //  3-> searchByTitle()
           // 4 -> searchByBrand()

            else -> println("Invalid option entered: $option");
        }
    }// else {
    //    println("Option Invalid - No notes stored");
   // }


fun addMilk(){

    val title = ScannerInput.readNextLine("enter milk name")
    val price = readNextDouble("enter the price")
    val proteinAmount = readNextDouble("enter protein content")
    val brand = ScannerInput.readNextLine("enter brand")
    val litres = readNextDouble("enter amount of litres")

    val isAdded = milkapi.add(proteinMilk(title,price,proteinAmount,brand,litres))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}


fun updateMilk(){
    listAllMilks()
    if (milkapi.numberOfMilks() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the milk to update: ")
        if (milkapi.isValidIndex(indexToUpdate)) {
            val milkTitle = ScannerInput.readNextLine("Enter a title for the Milk: ")
            var price = readNextDouble("Enter a price ")
            while(!isValidPrice(price)){
                price = readNextDouble("Value cannot be 0, enter correct price")
            }
            var proteinAmount = readNextDouble("Enter a protein content for the milk: ")
            while(!isValidProteinAmount(proteinAmount)){
                proteinAmount = readNextDouble("Enter a protein content for the milk: ")
            }
            var brand = ScannerInput.readNextLine("Enter a brand for the milk: ")
           var litres = ScannerInput.readNextDouble("enter amount of litres")

              if (milkapi.updateMilks(indexToUpdate, proteinMilk(milkTitle, price, proteinAmount, brand,litres))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no milk for this index number")
        }
    }

}


fun deleteMilk(){
    listAllMilks()
    if (milkapi.numberOfMilks() > 0) {
        val indexToDelete = readNextInt("Enter the index of the milk to delete: ")
         val milkToDelete = milkapi.delete(indexToDelete)
        if (milkToDelete != null) {
            println("Delete Successful! Deleted milk: ${milkToDelete.milkTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}



fun listAllMilks(){

    println(milkapi.listAllMilks())
}

fun save() {
    try {
        milkapi.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        milkapi.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp(){
    println("Exiting...bye")
    System.exit(0)
}



