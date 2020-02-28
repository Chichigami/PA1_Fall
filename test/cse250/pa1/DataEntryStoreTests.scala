/**
 * cse250.pa1.DataEntryStoreTests.scala
 *
 * Copyright 2020 Andrew Hughes (ahughes6@buffalo.edu)
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-nc-sa/4.0/.
 *
 * Submission author
 * UBIT:garyfeng
 * Person#:50242102
 *
 * Collaborators (include UBIT name of each, comma separated):
 * UBIT:
 */
package cse250.pa1

import cse250.objects.{AssessmentUtilities, EmbeddedEmpty, TaxParcel}
import org.scalatest.{BeforeAndAfter, FlatSpec}


class DataEntryStoreTests extends FlatSpec with BeforeAndAfter {
  var dataStore: DataEntryStore[TaxParcel] = _

  before {
    // This code will execute before each test.
    dataStore = new DataEntryStore[TaxParcel](AssessmentUtilities.maxCapacity)
  }

  behavior of "DataEntryStore.dataArray"
  it should "should be initialized to all empty nodes" in {
    for(entry <- dataStore.dataArray)
      assert(entry == dataStore.emptyNode)
  }

  behavior of "DataEntryStore.head and DataEntryStore.tail"
  it should "always be initialized to -1" in {
    assert(dataStore.headIndex == -1)
    assert(dataStore.tailIndex == -1)
  }

  behavior of "DataEntryStore.length"
  it should "always be initialized to 0" in {
    assert(dataStore.length == 0)
  }

  behavior of "DataEntryStore.insert"
  it should "the inserted entry should be the same" in {
    val entries = AssessmentUtilities.loadAssessmentEntries(AssessmentUtilities.filename, AssessmentUtilities.maxCapacity)
    for (i <- 0 until entries.length) {
      dataStore.insert(entries(i))
      assert(dataStore.apply(i) == entries(i))
      assert(dataStore.length == i + 1)

    }
  }

  behavior of "DataEntryStore.remove"
  it should "add and remove entry and dupes" in {
    val entries = AssessmentUtilities.loadAssessmentEntries(AssessmentUtilities.filename, AssessmentUtilities.maxCapacity)
    for (i <- 0 until entries.length) {
      dataStore.insert(entries(i))
      assert(dataStore.length == 1)
      dataStore.remove(entries(i))
      assert(dataStore.length == 0)
    }
  }

  behavior of "DataEntryStore.apply"
  it should "get the entry from index" in {
    val entries = AssessmentUtilities.loadAssessmentEntries(AssessmentUtilities.filename, AssessmentUtilities.maxCapacity)
    for (i <- 0 until entries.length) {
      dataStore.insert(entries(i))
      assert(dataStore.apply(i) == entries(i))
    }
  }

  behavior of "DataEntryStore.update"
  it should "update the head to null" in {
    val entries = AssessmentUtilities.loadAssessmentEntries(AssessmentUtilities.filename, AssessmentUtilities.maxCapacity)
    for (i <- 0 until entries.length) {
      dataStore.insert(entries(i))
    }
    val emptyParcel = new TaxParcel
    dataStore.update(0, emptyParcel)
    assert(dataStore.head == emptyParcel)
  }

  behavior of "DataEntryStore.iterator"
  it should "..." in {

  }
}
