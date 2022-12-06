import java.io.File

fun main(args: Array<String>) {

    // Read file from classpath (/resources folder)
    val dataStream: CharArray = {}.javaClass.getResource("input.txt").readText(Charsets.UTF_8).toCharArray()
    processData(dataStream, 4, 1)
    processData(dataStream, 14, 2)
}

// Process data with a moving window that will slide to the right
// of the first of any matching characters until it gets the signal that
// there are no more matching characters.
fun processData(dataStream: CharArray, windowSize: Int, problemNum: Int) {
    var start = 0
    var found = false
    while (start < dataStream.size && !found) {
        val result = findDuplicates(dataStream, start, windowSize)
        found = result < 0
        if (!found)
            start += (result - start) + 1 // Move the window to just past the duplicate value
    }

    print ("Problem " + problemNum + ": Start of stream is at position: " + (start + windowSize) + " with marker values: ")
    for (i in start until start+windowSize) {
        print(dataStream[i])
    }

    println()

}
fun findDuplicates(dataStream: CharArray, start: Int, windowSize: Int): Int {
    val letterMap: HashMap<Char, Int> = HashMap<Char, Int> ()
    var index = start
    var dupeFound = false
    do {
        val currentLetter: Char = dataStream[index]
        dupeFound = letterMap.containsKey(currentLetter)
        // if we find a duplicate we should just skip out and return where the first
        // letter occurred, so we can shift the window past the duplicate letter.
        if (dupeFound) {
            return letterMap.get(currentLetter)!!
        }
        letterMap.put(currentLetter, index)
        index++
    } while (index < start + windowSize)
    return -1 // this indicates we had a clean scan
}