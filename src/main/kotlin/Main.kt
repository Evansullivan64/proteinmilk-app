


import controllers.milkAPI

import models.proteinMilk
import mu.KotlinLogging

import utils.ScannerInput
import utils.ScannerInput.readNextInt

import java.io.File

private val logger = KotlinLogging.logger {}
//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
//private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))
//private val noteAPI = NoteAPI(YAMLSerializer(File("notes.yml")))
private val milkapi = milkAPI()


fun main(args: Array<String>) {
    runMenu()
}

fun runMenu() {
    do {
        val option = mainMenu()

        when (option) {
            1  -> addMilk()
            2  -> listMilks()
          //  3  -> updateMilk()
           // 4  -> deleteMilk()
          //  5 -> archiveMilk()
          //  6 -> load()
          //  7 -> save()
           // 0  -> exitApp()
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
         > |   1) Add a note                |
         > |   2) List all notes methods    |
         > |   3) Update a note             |
         > |   4) Delete a note             |
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
          //  1-> listAllMilks()
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
    val price = ScannerInput.readNextDouble("enter the price")
    val proteinAmount = ScannerInput.readNextDouble("enter protein content")
    val brand = ScannerInput.readNextLine("enter brand")
    val litres = ScannerInput.readNextDouble("enter amount of litres")

    val isAdded = milkapi.add(proteinMilk(title,price,proteinAmount,brand,litres))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

