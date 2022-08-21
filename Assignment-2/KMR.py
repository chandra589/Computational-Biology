from math import log2


class KMR:

	def __init__(self, inputstring):
		self.istring = inputstring
		self.kmrarrays = {}

	def findkmrarrays(self):
		kmarray = []
		for c in self.istring:
			kmarray.append(ord(c))

		#create a dictionary
		dict = {}
		index = 1
		for val in kmarray:
			if val in dict.keys():
				continue
			dict[val] = index
			index +=1

		sorted_keys = radixsort(list(dict.keys()))
		dict2 = {}
		index = 1
		for val in sorted_keys:
			dict2[val] = index
			index += 1
		
		
		finalkmarray = []
		index = 0
		for val in self.istring:
			finalkmarray.append(dict2[kmarray[index]])
			index += 1

		self.kmrarrays[0] = finalkmarray

		lenofinputstring = len(self.istring)

		k = int(log2(lenofinputstring))
		for x in range(1, k+1):
			newkmarray = []
			jump = pow(2, x - 1)
			for index in range(lenofinputstring):
				shift = index + jump
				if (shift > lenofinputstring - 1):
					valc = finalkmarray[index]*lenofinputstring
				else:
					valc = finalkmarray[index]*lenofinputstring + finalkmarray[shift]

				newkmarray.append(valc)

			#create a dictionary
			dict = {}
			index = 1
			for val in newkmarray:
				if val in dict.keys():
					continue
				dict[val] = index
				index +=1

			sorted_keys = radixsort(list(dict.keys()))
			dict2 = {}
			index = 1
			for val in sorted_keys:
				dict2[val] = index
				index += 1

			
			finalkmarray = []
			index = 0
			for val in self.istring:
				finalkmarray.append(dict2[newkmarray[index]])
				index += 1

			self.kmrarrays[x] = finalkmarray


def radixsort(kmarray):
	maxEl = max(kmarray)
	D = 1
	while maxEl > 0:
		maxEl /= 10
		D += 1	
	placeVal = 1
	outputArray = kmarray
	while D > 0:
		outputArray = countingroutineforradix(outputArray, placeVal)
		placeVal *= 10  
		D -= 1
	return outputArray

def countingroutineforradix(inputArray, placeval):
	countArray = [0] * 10
	inputSize = len(inputArray)

	for i in range(inputSize): 
		placeElement = (inputArray[i] // placeval) % 10
		countArray[placeElement] += 1

	for i in range(1, 10):
		countArray[i] += countArray[i-1]

	outputArray = [0] * inputSize
	i = inputSize - 1
	while i >= 0:
		currentEl = inputArray[i]
		placeElement = (inputArray[i] // placeval) % 10
		countArray[placeElement] -= 1
		newPosition = countArray[placeElement]
		outputArray[newPosition] = currentEl
		i -= 1
		
	return outputArray


if __name__ == '__main__':
	
	mainstring = ''
	numberofqueries = ''
	inputqueries = []
	with open('in.txt') as f:
		mainstring = f.readline()
		numberofqueries = f.readline()
		inputqueries = f.readlines()

	total_queries = int(numberofqueries)
	queries = []
	for query in inputqueries:
		words = query.split( )
		queries.append(words)

	#mainstring = 'banana'
	#mainstring += '$'
	mainstring = mainstring[:-1]
	obj = KMR(mainstring)
	obj.findkmrarrays()

	f = open('out.txt', 'w')

	for query in queries:
		i = int(query[0])
		j = int(query[1])
		l = int(query[2])

		kmarrayindex = int(log2(l))
		lcalc = pow(2, kmarrayindex)

		kmrarray = obj.kmrarrays[kmarrayindex]
		check1 = (kmrarray[i-1] == kmrarray[j-1])
		check2 = True
		if (l != lcalc):
			newi = i + l - lcalc
			newj = j + l - lcalc
			check2 = (kmrarray[newi-1] == kmrarray[newj-1])

		if (check1 and check2):
			f.write('YES')
		else:
			f.write('NO')
		
		f.write('\n')
	
	f.close()

	