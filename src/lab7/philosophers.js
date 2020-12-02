var Fork = function() {
    this.state = 0;
    return this;
};

Fork.prototype.acquire = function(cb) {
    let waitTime = 1;
    const takeFork = () => {
        if (this.state == 1) { // BEB
            waitTime *= 2;  
            setTimeout(takeFork, waitTime);
        } else {
            this.state = 1;
            if (cb) cb();
        }
    }

    takeFork();
};

Fork.prototype.release = function(cb) {
    this.state = 0;
    if (cb) cb();
};

const printForks = () => {
    console.log(forks.map(fork => fork.state))
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    return this;
};

Philosopher.prototype.startNaive = function(count) {
    let forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    const eat = (id, count) => {
        printForks();
        forks[f1].acquire(() => {
            printForks();
            forks[f2].acquire(() => {
                printForks();
                setTimeout(() => {
                    printForks();
                    forks[f1].release(() => {
                        printForks();
                        forks[f2].release(() => {
                            printForks();
                            count--;
                            if (count > 0) {
                                setTimeout(() => eat(id, count));
                            }
                        });
                    });
                }, 2);
            });
        });
    }

    setTimeout(() => eat(id, count), 0);
};

Philosopher.prototype.startAsym = function(count) {
    let forks = this.forks,
        id = this.id,
        f1 = id % 2 == 0 ? this.f1 : this.f2,
        f2 = id % 2 == 0 ? this.f2 : this.f1;

        const eat = (id, count) => {
            printForks();
            forks[f1].acquire(() => {
                printForks();
                forks[f2].acquire(() => {
                    printForks();
                    setTimeout(() => {
                        printForks();
                        forks[f1].release(() => {
                            printForks();
                            forks[f2].release(() => {
                                printForks();
                                count--;
                                if (count > 0) {
                                    setTimeout(() => eat(id, count));
                                }
                            });
                        });
                    }, 2);
                });
            });
        }

        setTimeout(() => eat(id, count), 0);
};

Philosopher.prototype.startConductor = function(count) {

    if (count > 0) {
        conductor.requestForks(this, count);
    }
};

Philosopher.prototype.giveForks = function(count) {
    setTimeout(() => conductor.returnForks(this, count), 2);
}

var Conductor = function() {
    this.philosophersCounter = 0;
    return this;
}

Conductor.prototype.requestForks = function(philosopher, count) {
    let forks = philosopher.forks,
        id = philosopher.id,
        f1 = philosopher.f1,
        f2 = philosopher.f2;

        printForks();
    if (forks[f1].state === 0 && forks[f2].state === 0 && this.philosophersCounter < N) {
        forks[f1].state = 1;
        forks[f2].state = 1;   
        this.philosophersCounter += 1; 
        printForks();
        philosopher.giveForks(count);
    } else {
        setTimeout(() => this.requestForks(philosopher, count), 2);
    }
}

Conductor.prototype.returnForks = function(philosopher, count) {
    // TODO
    let f1 = philosopher.f1,
        f2 = philosopher.f2,
        forks = philosopher.forks,
        id = philosopher.id;

    forks[f1].state = 0;
    forks[f2].state = 0;
    this.philosophersCounter -= 1;
    philosopher.startConductor(count - 1);

}

let N = 5;
let forks = [];
let philosophers = [];
let conductor = new Conductor();

for (let i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (let i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (let i = 0; i < N; i++) {
    // philosophers[i].startNaive(2000);
    // philosophers[i].startAsym(10);
    philosophers[i].startConductor(10);
}