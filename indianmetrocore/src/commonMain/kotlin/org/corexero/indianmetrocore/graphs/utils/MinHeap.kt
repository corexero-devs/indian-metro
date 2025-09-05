package org.corexero.indianmetrocore.graphs.utils

class MinHeap<T>(private val comparator: Comparator<T>) {
    private val a = mutableListOf<T>()

    fun isEmpty() = a.isEmpty()
    fun isNotEmpty() = a.isNotEmpty()
    val size get() = a.size

    fun add(x: T) {
        a.add(x)
        siftUp(a.lastIndex)
    }

    fun poll(): T {
        if (a.isEmpty()) throw NoSuchElementException("Heap is empty")
        val res = a[0]
        val last = a.removeAt(a.lastIndex)
        if (a.isNotEmpty()) {
            a[0] = last
            siftDown(0)
        }
        return res
    }

    private fun siftUp(i0: Int) {
        var i = i0
        while (i > 0) {
            val p = (i - 1) / 2
            if (comparator.compare(a[i], a[p]) >= 0) break
            a.swap(i, p)
            i = p
        }
    }

    private fun siftDown(i0: Int) {
        var i = i0
        val n = a.size
        while (true) {
            val l = 2 * i + 1
            val r = l + 1
            var m = i
            if (l < n && comparator.compare(a[l], a[m]) < 0) m = l
            if (r < n && comparator.compare(a[r], a[m]) < 0) m = r
            if (m == i) break
            a.swap(i, m)
            i = m
        }
    }

    private fun MutableList<T>.swap(i: Int, j: Int) {
        val t = this[i]
        this[i] = this[j]
        this[j] = t
    }
}