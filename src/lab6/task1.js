const getDelay = () => Math.floor((Math.random()*1000)+500);
 
function task1 (n, current) {
    if (n == current) {
        console.log("done");
        return;
    }
    current++;
    console.log("1");
    setTimeout(() => task2(n, current), getDelay());
}
 
function task2 (n, current) {
    console.log("2");
    setTimeout(() => task3(n, current), getDelay());
}
 
function task3 (n, current) {
    console.log("3");
    setTimeout(() => task1(n, current), getDelay());
}
 
function loop (n) {
    setTimeout(() => task1(n, 0), getDelay);
}

loop(4);