package org.jetbrains.ring

open class IntStreamBenchmark {
    private var _data: Iterable<Int>? = null
    val data: Iterable<Int>
        get() = _data!!

    fun setup() {
        _data = intValues(BENCHMARK_SIZE)
    }
    
    //Benchmark
    fun copy(): List<Int> {
        return data.asSequence().toList()
    }
    
    //Benchmark
    fun copyManual(): List<Int> {
        val list = ArrayList<Int>()
        for (item in data.asSequence()) {
            list.add(item)
        }
        return list
    }
    
    //Benchmark
    fun filterAndCount(): Int {
        return data.asSequence().filter { filterLoad(it) }.count()
    }
    
    //Benchmark
    fun filterAndMap() {
        for (item in data.asSequence().filter { filterLoad(it) }.map { mapLoad(it) })
            Blackhole.consume(item)
    }
    
    //Benchmark
    fun filterAndMapManual() {
        for (it in data.asSequence()) {
            if (filterLoad(it)) {
                val item = mapLoad(it)
                Blackhole.consume(item)
            }
        }
    }
    
    //Benchmark
    fun filter() {
        for (item in data.asSequence().filter { filterLoad(it) })
            Blackhole.consume(item)
    }
    
    //Benchmark
    fun filterManual(){
        for (it in data.asSequence()) {
            if (filterLoad(it))
                Blackhole.consume(it)
        }
    }
    
    //Benchmark
    fun countFilteredManual(): Int {
        var count = 0
        for (it in data.asSequence()) {
            if (filterLoad(it))
                count++
        }
        return count
    }
    
    //Benchmark
    fun countFiltered(): Int {
        return data.asSequence().count { filterLoad(it) }
    }
    
    //Benchmark
    fun countFilteredLocal(): Int {
        return data.asSequence().cnt { filterLoad(it) }
    }
    
    //Benchmark
    fun reduce(): Int {
        return data.asSequence().fold(0) {acc, it -> if (filterLoad(it)) acc + 1 else acc }
    }
}