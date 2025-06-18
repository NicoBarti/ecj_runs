import pandas as pd
import matplotlib.pyplot as plt
import matplotlib as mlp
import numpy as np
import seaborn as sns

mlp.rcParams["text.usetex"]

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
                    bestFit = np.round(float(nextLine.split()[1]),2)
                    bestGen = np.array(f.readline().split()).astype(float).round(2)
                    findBest = False
                    break

bestGen = np.array(bestGen).astype(float).round(2)

path = 'ecj/src/main/java/ec/patientCare/care_details.stat'

with open(path, 'r') as f:
    for line in f:
        if line.split()[0] == "capacity":
            capacity = int(line.split()[1])
            continue
        if line.split()[0] == "NumPatients":
            numPatients = int(line.split()[1])
            continue
        if line.split()[0] == "weeks":
            weeks = int(line.split()[1])
            continue
        if line.split()[0] == "SEVERITY_ALLOCATION":
            SEVERITY_ALLOCATION = float(line.split()[1]).__round__(2)
            continue
        if line.split()[0] == "CONTINUITY_ALLOCATION":
            CONTINUITY_ALLOCATION = float(line.split()[1]).__round__(2)
            continue
        if line.split()[0] == "DISEASE_SEVERITY":
            DISEASE_SEVERITY = float(line.split()[1]).__round__(2)
            continue
        if line.split()[0] == "LEARNING_RATE":
            LEARNING_RATE = float(line.split()[1]).__round__(2)
            continue
        if line.split()[0] == "SUBJECTIVE_INITIATIVE":
            SUBJECTIVE_INITIATIVE = float(line.split()[1]).__round__(2)
            continue
        if line.split()[0] == "EXP_POS":
            EXP_POS = float(line.split()[1]).__round__(2)
            continue
        if line.split()[0] == "EXP_NEG":
            EXP_NEG = float(line.split()[1]).__round__(2)
            continue
        if line.split()[0] == "t":
            t = float(line.split()[1]).__round__(2)
            continue
        if line.split()[0] == "fit:":
            fitTitle = line

d = {'fit': fit,
     'lamda': np.array(genome)[:,0].astype('float'),
     'Psy': np.array(genome)[:,1].astype('float')}

data = pd.DataFrame(d)
with pd.option_context('display.max_rows', None,
                       'display.max_columns', None,
                       'display.precision', 3,
                       ):
    print(data)

newLine = "\n"
tit = f"gen {generations} size {popsize} mut {mutType} p {mutProb} sd {mutStDev} // BEST Fit: {bestFit} $\lambda$: {bestGen[0]} $\Psi$: {bestGen[1]} {newLine} MODEL: $\\varsigma$: {weeks} N: {numPatients} $\delta$: {DISEASE_SEVERITY} $\lambda$: {LEARNING_RATE} $\\tau$: {t} $\\rho$: {EXP_POS} $\eta$: {EXP_NEG} $\Psi$: {SUBJECTIVE_INITIATIVE} $\\alpha$: {capacity} {newLine} {fitTitle}"

fig = sns.pairplot(data=data)
fig.figure.suptitle(tit)
fig.figure.subplots_adjust(top=0.87)
plt.show()

