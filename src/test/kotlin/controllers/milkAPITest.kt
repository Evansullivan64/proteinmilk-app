


package controllers

import models.proteinMilk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import java.util.Locale
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class milkAPITest {

    private var milk1: proteinMilk? = null
    private var milk2: proteinMilk? = null
    private var milk3: proteinMilk? = null
    private var milk4: proteinMilk? = null
    private var milk5: proteinMilk? = null
    private var populatedMilks: milkAPI? = milkAPI(XMLSerializer(File("milks.xml")))
    private var emptyMilks: milkAPI? = milkAPI(XMLSerializer(File("milks.xml")))

    @BeforeEach
    fun setup() {
        milk1 = proteinMilk("protein gold", 1.65, 33.00, "avonmore", .8, true)
        milk2 = proteinMilk("protein silver", 1.00, 73.00, "tesco", 1.23, false)
        milk3 = proteinMilk("protein bronze", 1.23, 14.00, "aldi", 1.43, true)
        milk4 = proteinMilk("protein stone", 2.65, 17.00, "tesco", 1.54, false)
        milk5 = proteinMilk("best protein", 2.65, 23.00, "avonmore", 1.75, false)

        // adding 5 Note to the notes api
        populatedMilks!!.add(milk1!!)
        populatedMilks!!.add(milk2!!)
        populatedMilks!!.add(milk3!!)
        populatedMilks!!.add(milk4!!)
        populatedMilks!!.add(milk5!!)
    }

    @AfterEach
    fun tearDown() {
        milk1 = null
        milk2 = null
        milk3 = null
        milk4 = null
        milk5 = null
        populatedMilks = null
        emptyMilks = null
    }

    @Nested
    inner class Addmilks {
        @Test
        fun `adding a milk to a populated list adds to ArrayList`() {
            val newMilk = proteinMilk("whey protein milk", 1.37, 31.00, "avonmore", 1.43, true)
            assertEquals(5, populatedMilks!!.numberOfMilks())
            assertTrue(populatedMilks!!.add(newMilk))
            assertEquals(6, populatedMilks!!.numberOfMilks())
            assertEquals(newMilk, populatedMilks!!.findMilks(populatedMilks!!.numberOfMilks() - 1))
        }

        @Test
        fun `adding a Milk to an empty list adds to ArrayList`() {
            val newMilk = proteinMilk("whey protein milk with coco", 1.00, 23.00, "tesco", 1.43, false)
            assertEquals(0, emptyMilks!!.numberOfMilks())
            assertTrue(emptyMilks!!.add(newMilk))
            assertEquals(1, emptyMilks!!.numberOfMilks())
            assertEquals(newMilk, emptyMilks!!.findMilks(emptyMilks!!.numberOfMilks() - 1))
        }
    }

    @Nested
    inner class ListNotes {

        @Test
        fun `listMilksByBrand returns No milks Stored message when ArrayList is empty`() {
            assertEquals(0, emptyMilks!!.numberOfMilks())
            assertTrue(emptyMilks!!.listMilksByBrand("avonmore").lowercase().contains("no milks"))
        }

        @Test
        fun `listMilksByBrand returns milks when ArrayList has notes stored`() {
            assertEquals(5, populatedMilks!!.numberOfMilks())
            val milksString = populatedMilks!!.listMilksByBrand("avonmore").lowercase()
            assertFalse(milksString.contains("tesco"))
            assertFalse(milksString.contains("aldi"))
            assertFalse(milksString.contains("lidl"))
            assertTrue(milksString.contains("avonmore"))
            assertTrue(milksString.contains("avonmore"))
        }

        @Test
        fun `listAllMilks returns No milks Stored message when ArrayList is empty`() {
            assertEquals(0, emptyMilks!!.numberOfMilks())
            assertTrue(emptyMilks!!.listAllMilks().lowercase().contains("no milks"))
        }

        @Test
        fun `listAllMilks returns Notes when ArrayList has notes stored`() {
            assertEquals(5, populatedMilks!!.numberOfMilks())
            val milksString = populatedMilks!!.listAllMilks().lowercase()
            assertTrue(milksString.contains("milk1"))
            assertTrue(milksString.contains("milk2"))
            assertTrue(milksString.contains("milk3"))
            assertTrue(milksString.contains("milk4"))
            assertTrue(milksString.contains("milk5"))
        }

        @Test
        fun `listFavouriteMilks returns no favourited milks when ArrayList is empty`() {
            assertEquals(0, emptyMilks!!.numberOfFavouriteMilks())
            assertTrue(
                emptyMilks!!.listFavouriteMilk().lowercase().contains("you have no favourite milks")
            )
        }

        @Test
        fun `listFavouriteMilks returns favourited milks when ArrayList has archived notes stored`() {
            assertEquals(2, populatedMilks!!.numberOfFavouriteMilks())
            val favouritemilksString = populatedMilks!!.listFavouriteMilk().lowercase(Locale.getDefault())
            assertTrue(favouritemilksString.contains("milk1"))
            assertFalse(favouritemilksString.contains("milk2"))
            assertTrue(favouritemilksString.contains("milk3"))
            assertFalse(favouritemilksString.contains("milk4"))
            assertFalse(favouritemilksString.contains("milk5"))
        }

        @Test
        fun `listMilksByPrice returns No Milks when ArrayList is empty`() {
            assertEquals(0, emptyMilks!!.numberOfMilks())
            assertTrue(
                emptyMilks!!.listMilksByPrice(34.00).lowercase().contains("no milks")
            )
        }

        @Test
        fun `listMilksByPrice returns no notes when no milks of that priority exist`() {

            assertEquals(5, populatedMilks!!.numberOfMilks())
            val priority2String = populatedMilks!!.listMilksByPrice(2.00).lowercase()
            assertTrue(priority2String.contains("no notes"))
        }

        @Test
        fun `listMilksByPrice returns all milks that match that price when milks of that price exist`() {
            assertEquals(5, populatedMilks!!.numberOfMilks())
            val milksprice1String = populatedMilks!!.listMilksByPrice(1.00).lowercase()
            assertTrue(milksprice1String.contains("protein silver"))
            assertFalse(milksprice1String.contains("protein gold"))
            assertFalse(milksprice1String.contains("protein bronze"))
            assertFalse(milksprice1String.contains("protein stone"))
            assertFalse(milksprice1String.contains("best protein"))

            val milksPrice265String = populatedMilks!!.listMilksByPrice(2.65).lowercase(Locale.getDefault())

            assertFalse(milksPrice265String.contains("protein silver"))
            assertTrue(milksPrice265String.contains("protein stone"))
            assertTrue(milksPrice265String.contains("best protein"))
            assertFalse(milksPrice265String.contains("protein bronze"))
            assertFalse(milksPrice265String.contains("protein gold"))
        }
    }

    @Nested
    inner class DeleteMilks {

        @Test
        fun `deleting a Milk that does not exist, returns null`() {
            assertNull(emptyMilks!!.delete(0))
            assertNull(populatedMilks!!.delete(-1))
            assertNull(populatedMilks!!.delete(5))
        }

        @Test
        fun `deleting a Milk that exists delete and returns deleted object`() {
            assertEquals(5, populatedMilks!!.numberOfMilks())
            assertEquals(milk5, populatedMilks!!.delete(4))
            assertEquals(4, populatedMilks!!.numberOfMilks())
            assertEquals(milk1, populatedMilks!!.delete(0))
            assertEquals(3, populatedMilks!!.numberOfMilks())
        }
    }

    @Nested
    inner class UpdateNotes {
        @Test
        fun `updating a milk that does not exist returns false`() {
            assertFalse(populatedMilks!!.updateMilks(6, proteinMilk("updating milk", 2.00, 55.00, "avonmore", 7.00, false)))
            assertFalse(populatedMilks!!.updateMilks(-1, proteinMilk("Updating milk", 5.00, 55.00, "Tesco", 5.00, false)))
            assertFalse(emptyMilks!!.updateMilks(0, proteinMilk("Updating milk", 6.00, 34.00, "centra", 3.00, false)))
        }

        @Test
        fun `updating a milk that exists returns true and updates`() {
            // check note 5 exists and check the contents
            assertEquals(milk5, populatedMilks!!.findMilks(4))
            assertEquals("best protein", populatedMilks!!.findMilks(4)!!.milkTitle)
            assertEquals("avonmore", populatedMilks!!.findMilks(4)!!.brand)
            assertEquals(2.65, populatedMilks!!.findMilks(4)!!.price)

            // update note 5 with new information and ensure contents updated successfully
            assertTrue(populatedMilks!!.updateMilks(4, proteinMilk("Updating milk", 2.00, 3.6, "Tesco", 69.00, false)))
            assertEquals("Updating milk", populatedMilks!!.findMilks(4)!!.milkTitle)
            assertEquals(2.00, populatedMilks!!.findMilks(4)!!.price)
            assertEquals(3.6, populatedMilks!!.findMilks(4)!!.proteinAmount)
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty notes.XML file.
            val storingMilks = milkAPI(XMLSerializer(File("milks.xml")))
            storingMilks.store()

            // Loading the empty notes.xml file into a new object
            val loadedNotes = milkAPI(XMLSerializer(File("milks.xml")))
            loadedNotes.load()

            // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(0, storingMilks.numberOfMilks())
            assertEquals(0, loadedNotes.numberOfMilks())
            assertEquals(storingMilks.numberOfMilks(), loadedNotes.numberOfMilks())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {
            // Storing 3 notes to the notes.XML file.
            val storingMilks = milkAPI(XMLSerializer(File("milks.xml")))
            storingMilks.add(milk4!!)
            storingMilks.add(milk5!!)
            storingMilks.add(milk2!!)
            storingMilks.store()

            // Loading notes.xml into a different collection
            val loadedNotes = milkAPI(XMLSerializer(File("milks.xml")))
            loadedNotes.load()

            // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(3, storingMilks.numberOfMilks())
            assertEquals(3, loadedNotes.numberOfMilks())
            assertEquals(storingMilks.numberOfMilks(), loadedNotes.numberOfMilks())
            assertEquals(storingMilks.findMilks(0), loadedNotes.findMilks(0))
            assertEquals(storingMilks.findMilks(1), loadedNotes.findMilks(1))
            assertEquals(storingMilks.findMilks(2), loadedNotes.findMilks(2))
        }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty notes.json file.
            val storingMilks = milkAPI(JSONSerializer(File("milks.json")))
            storingMilks.store()

            // Loading the empty notes.json file into a new object
            val loadedMilks = milkAPI(JSONSerializer(File("milks.json")))
            loadedMilks.load()

            // Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
            assertEquals(0, storingMilks.numberOfMilks())
            assertEquals(0, loadedMilks.numberOfMilks())
            assertEquals(storingMilks.numberOfMilks(), loadedMilks.numberOfMilks())
        }

        @Test
        fun `saving and loading an loaded collection in JSON doesn't loose data`() {
            // Storing 3 notes to the notes.json file.
            val storingMilks = milkAPI(JSONSerializer(File("milks.json")))
            storingMilks.add(milk4!!)
            storingMilks.add(milk5!!)
            storingMilks.add(milk2!!)
            storingMilks.store()

            // Loading notes.json into a different collection
            val loadedMilks = milkAPI(JSONSerializer(File("milks.json")))
            loadedMilks.load()

            // Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
            assertEquals(3, storingMilks.numberOfMilks())
            assertEquals(3, loadedMilks.numberOfMilks())
            assertEquals(storingMilks.numberOfMilks(), loadedMilks.numberOfMilks())
            assertEquals(storingMilks.findMilks(0), loadedMilks.findMilks(0))
            assertEquals(storingMilks.findMilks(1), loadedMilks.findMilks(1))
            assertEquals(storingMilks.findMilks(2), loadedMilks.findMilks(2))
        }
    }

    @Nested
    inner class favouriteMilks {
        @Test
        fun `Favouriting a milk that does not exist returns false`() {
            assertFalse(populatedMilks!!.favouriteMilk(6))
            assertFalse(populatedMilks!!.favouriteMilk(-1))
            assertFalse(emptyMilks!!.favouriteMilk(0))
        }

        @Test
        fun `favouriting  an already favourited milk returns false`() {
            assertTrue(populatedMilks!!.findMilks(2)!!.isMilkFavourited)
            assertFalse(populatedMilks!!.favouriteMilk(2))
        }

        @Test
        fun `favouriting a milk that exists returns true and archives`() {
            assertFalse(populatedMilks!!.findMilks(1)!!.isMilkFavourited)
            assertTrue(populatedMilks!!.favouriteMilk(1))
            assertTrue(populatedMilks!!.findMilks(1)!!.isMilkFavourited)
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfMilksCalculatedCorrectly() {
            assertEquals(5, populatedMilks!!.numberOfMilks())
            assertEquals(0, emptyMilks!!.numberOfMilks())
        }

        @Test
        fun numberOfFavouriteMilksCalculatedCorrectly() {
            assertEquals(2, populatedMilks!!.numberOfFavouriteMilks())
            assertEquals(0, emptyMilks!!.numberOfFavouriteMilks())
        }
    }
}
