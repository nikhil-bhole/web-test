# Linter Usage

**web-tests** project has  **custom Python linter** designed to enforce coding standards and improve code quality.

This linter:

✅ Detects common style violations  
✅ Fails GitHub Actions if issues are found

---

## 📌 **Linting Rules**
The linter enforces the following checks:

### **1️⃣ Unused Variables Check**
✔️ Detects variables that are declared but never used  
✔️ Excludes method parameters and inline object creation

**🔴 Example Violation:**
```java
public class Example {
    int unusedVar; // ❌ Unused variable
}
```

---

### **2️⃣ Opening Brace Placement Check**
✔️ Ensures `{` is **not placed on a new line** unless required

**🔴 Example Violation:**
```java
public void method()  
{
    // ❌ Incorrect: Opening brace should be on the same line
}
```
✅ **Correct:**
```java
public void method() {
    // ✅ Correct
}
```

---

### **3️⃣ End-of-File Newline Check**
✔️ Ensures the file ends with exactly **one** newline  
✔️ Compliant with GitHub file formatting requirements

**🔴 Example Violation:**
```java
public class Example {
    int x;
} // ❌ Missing newline at EOF
```
✅ **Correct:**
```java
public class Example {
    int x;
}

```

---

### **4️⃣ Method Brace Spacing Check**
✔️ Ensures exactly **one space** between `()` and `{` in method definitions

**🔴 Example Violation:**
```java
public WebElement $(){  // ❌ Incorrect: No space
    return element;
}
```
✅ **Correct:**
```java
public WebElement $() {  // ✅ Correct
    return element;
}
```

---

## ⚙️ **Setup & Usage**

### **1️⃣ Running the Linter Locally**
```sh
python java_linter.py
```

