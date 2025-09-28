# Assignment 1 — Algorithms & Performance Analysis

This project is part of **Assignment 1** for the Algorithms course.  
It implements and compares multiple sorting and selection algorithms, measuring their performance with detailed metrics.

---

##  Features
- **MergeSort, QuickSort, HeapSort** implementations
- **Deterministic Selection (Median of Medians)** algorithm
- Performance tracking with a custom `Metrics` utility
- Unit tests using **JUnit 5**
- CLI interface to run experiments and export results to CSV
- Graphs for runtime comparison

---

##  Project Structure
```
Assignment1/
 ├── src/main/java/org/example/        # Main algorithms and CLI
 │    ├── sort/                        # Sorting algorithms
 │    ├── select/                      # Selection algorithms
 │    └── util/                        # Metrics & helpers
 ├── src/test/java/org/example/        # Unit tests
 ├── results/                          # CSV output & plots
 ├── plot_results.py                   # Python plotting script
 ├── pom.xml                           # Maven configuration
 └── README.md                         # Project documentation
```

---

##  Setup & Run
1. Clone the repository:
   ```bash
   git clone https://github.com/IDGAFFM/Assignment1.git
   cd Assignment1
   ```

2. Build & run tests:
   ```bash
   mvn clean test
   ```

3. Run CLI (example: MergeSort with `n=10000`, `trials=3`):
   ```bash
mvn exec:java -Dexec.args="quicksort 10000 5 res/quicksort.csv"
   ```

This will generate a **CSV file** with performance metrics.

---

## 📊 Plotting Results

After generating CSV files, run the plotting script:

```bash
py -3 res.py res/quicksort.csv
```

![quicksort_performance.png](images/quicksort_performance.png)

```markdown
### 📈 Example Graph
Merge Sort
![MergeSort Performance](images/mergesort_performance.png)


Quick Sort
![quicksort_performance.png](images/quicksort_performance.png)
```

---

## ✅ Tests
Run unit tests to verify correctness:
```bash
mvn test
```

```
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  12.144 s
[INFO] Finished at: 2025-09-28T17:41:16+05:00
[INFO] ------------------------------------------------------------------------
```

---

## 📚 Technologies Used
- **Java 24**
- **Maven**
- **JUnit 5**
- **Matplotlib / Python (for plots)**

---

##  Author
- [Tynbayev Maden]  

### Quicksort Performance

![quicksort](images/quicksort_performance.png)

### Quicksort Performance

![quicksort](images/quicksort_performance.png)

### Mergesort Performance

![mergesort](images/mergesort_performance.png)
