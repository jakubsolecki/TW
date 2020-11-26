const getDelay = () => Math.floor((Math.random()*1000)+500);
let count;

function printAsync(s, cb) {
    setTimeout(() => {
        console.log(s);
        if (cb) cb();
    }, getDelay());
}
 
function lastPrint() {
    count--;
    if (count == 0) {
        console.log("Done");
    }
}

 // Napisz funkcje (bez korzytania z biblioteki async) wykonujaca 
 // rownolegle funkcje znajdujace sie w tablicy 
 // parallel_functions. Po zakonczeniu wszystkich funkcji
 // uruchamia sie funkcja final_function. Wskazowka:  zastosowc 
 // licznik zliczajacy wywolania funkcji rownoleglych 
 
 
function inparallel(parallel_functions, final_function) {
    count = taks.length
    taks.forEach(f => f(final_function));
}
 
const taks = 
[
    (cb) => printAsync("A",cb),
    (cb) => printAsync("B",cb),
    (cb) => printAsync("C",cb),
]

 inparallel(taks, lastPrint);
 
 // kolejnosc: A, B, C - dowolna, na koncu zawsze "Done" 