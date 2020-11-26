import waterfall from 'async/waterfall.js';

const tasks = [
    (arg, callback) => callback(null, arg+"2 "),
    (arg, callback) => callback(null, arg+"3 "),
    (arg, callback) => callback(null, arg+"1 ")
];

function loop (n) {
    let listOfTasks = [(callback) => callback(null, "1 ")]

    for (let i = 0; i < n; i++) {
        listOfTasks = listOfTasks.concat(tasks)
    }
    
    listOfTasks.pop();
    waterfall(listOfTasks, (error, result) => console.log(result));
    console.log("done");
}

loop(4);