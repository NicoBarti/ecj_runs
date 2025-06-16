import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns

path = 'ecj/src/main/java/ec/patientCare/info_learningAndSubjective.stat'

generations, popsize, mutProb, mutType, mutStDev = None,None,None,None,None
fit = []
genome = []
with open(path, 'r') as f:
    for line in f:
        #Lee el encabezado
        if line.split()[0] == "inicio":
            continue
        if line.split()[0] == "generations:":
            generations = int(line.split()[1])
            continue
        if line.split()[0] == "popsize:":
            popsize = int(line.split()[1])
            continue
        if line.split()[0] == "mutProb:":
            mutProb = float(line.split()[1])
            continue
        if line.split()[0] == "mutType:":
            mutType = line.split()[1]
            continue
        if line.split()[0] == "mutStDev:":
            mutStDev = float(line.split()[1])
            continue
        if line.split()[0] == "fin":
            continue
        # Comienza a leer individuos:
        if line.split()[0] == 'Evaluated:':
            True
        else:
            if line.split()[0] == 'Fitness:':
                fit.append(float(line.split()[1])*-1) ## convertst to possitive fittnes
            else:
                genome.append(line.split())


# read the best of run individual from stat object
path = 'ecj/src/main/java/ec/patientCare/out.stat'

bestFit = None
bestGen = []
with open(path, 'r') as f:
    for line in f:
        if line == 'Best Individual of Run:\n':
            findBest = True
            while findBest:
                nextLine = f.readline()
                if nextLine.split()[0] == "Fitness:":
                    bestFit = float(nextLine.split()[1])
                    bestGen = np.array(f.readline().split()).astype(float).round(2)
                    findBest = False
                    break

bestGen = np.array(bestGen).astype(float).round(2)

d = {'fit': fit,
     'lamda': np.array(genome)[:,0].astype('float'),
     'Psy': np.array(genome)[:,1].astype('float')}

data = pd.DataFrame(d)
with pd.option_context('display.max_rows', None,
                       'display.max_columns', None,
                       'display.precision', 3,
                       ):
    print(data)
tit = f"gen {generations} size {popsize} mut {mutType} p {mutProb} sd {mutStDev} // BEST lambda: {bestGen[0]} Psi: {bestGen[1]} "
fig = sns.pairplot(data=data)
fig.figure.suptitle(tit)
fig.figure.subplots_adjust(top=0.95)
plt.show()

