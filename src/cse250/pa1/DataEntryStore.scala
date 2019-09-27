/**
 * DataEntryStore.scala
 *
 * Copyright 2019 Andrew Hughes (ahughes6@buffalo.edu)
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-nc-sa/4.0/.
 *
 * Submission author
 * UBIT: garyfeng
 * Person#: 50242102
 *
 * Collaborators (include UBIT name of each, comma separated):
 * UBIT:
 */
package cse250.pa1

import cse250.objects.EmbeddedListNode

class DataEntryStore[A >: Null <: AnyRef](private val capacity: Int = 100)
  extends collection.mutable.Seq[A] {
  private val dataArray = Array.fill[EmbeddedListNode[A]](capacity)(new EmbeddedListNode[A])
  private var headIndex = -1
  private var tailIndex = -1
  private var numStored = 0
  var wrapped: Boolean = false

  /** Inserts element to tail of list. */
  def insert(elem: A): Unit = {
    var previousNode = 0
    if (numStored == 0) {
      headIndex = 0
      tailIndex = 0
      dataArray(tailIndex).value = elem
    } else if (numStored == capacity) { //wrapping
      previousNode = tailIndex
      tailIndex = headIndex
      headIndex += 1
      dataArray(tailIndex).value = elem
      dataArray(tailIndex).prev = previousNode
      dataArray(tailIndex).next = -1
      dataArray(previousNode).next = tailIndex
      dataArray(headIndex).prev = -1
      numStored -= 1
    } else { // normal insert
      previousNode = tailIndex
      tailIndex += 1
      dataArray(tailIndex).value = elem
      dataArray(tailIndex).prev = previousNode
      dataArray(previousNode).next = tailIndex
    }
    numStored += 1
  }

  /** Removes all copies of the given element. */
  def remove(elem: A): Boolean = {
    var positionPrev = 0
    var positionNext = 0
    var doesElemExist = false
    for (i <- dataArray.iterator){
      if (i.value == elem){
        doesElemExist = true
        positionPrev = i.prev //setting left and right node of cleaned to each other
        positionNext = i.next
        if (positionPrev != -1){
          dataArray(positionPrev).next = positionNext
        }
        if (positionNext != -1){
          dataArray(positionNext).prev = positionPrev
        }
        i.value = null //cleaning node
        i.prev = -1
        i.next = -1
        numStored -= 1
      }
    }
    doesElemExist
  }

  /** Returns the count of nodes containing given entry. */
  def countEntry(entry: A): Int = {
    var sum = 0
    for (i <- dataArray.iterator){
      if (i.value == entry){
       sum += 1
      }
    }
    sum
  }

  /** Gets the element at the specified index. */
  override def apply(idx: Int): A = {
    dataArray(idx).value
  }

  /** Replaces element at given index with a new value. */
  override def update(idx: Int, elem: A): Unit = {
    dataArray(idx).value = elem
  }

  /** Returns an Iterator that can be used only once. */
  def iterator: Iterator[A] = new Iterator[A] {
    private var current = headIndex

    override def hasNext: Boolean = current != -1

    override def next(): A = {
      val prev = current
      current = dataArray(current).next
      dataArray(prev).value
    }
  }

  /** Returns the length of the stored list. */
  override def length: Int = numStored

  override def toString: String = if (numStored == 0) "" else this.iterator.addString(new StringBuilder, "\n").result()
}
