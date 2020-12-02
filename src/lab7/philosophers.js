var Fork = function() {
    this.state = 0;
    return this;
};

Fork.prototype.acquire = function(cb) {
    let waitTime = 1;
    const takeFork = () => {
        if (this.state == 1) {
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
    let forks = this.forks,
    f1 = this.f1,
    f2 = this.f2,
    id = this.id;
};

let N = 5;
let forks = [];
let philosophers = []
for (let i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (let i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (let i = 0; i < N; i++) {
    // philosophers[i].startNaive(2000);
    philosophers[i].startAsym(10);
    // philosophers[i].startNaive(2000);
}