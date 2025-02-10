import re
import os
import sys

SRC_FOLDER = "src"

# ANSI Color Codes
RED = "\033[91m"
GREEN = "\033[92m"
CYAN = "\033[96m"
RESET = "\033[0m"

# Regex patterns
variable_declaration_pattern = re.compile(r"\b(?:int|double|float|long|short|char|boolean|String|List|ArrayList)\s+(\w+)\s*(?:=|;|\()")
brace_violation_pattern = re.compile(r"^\s*\{")  # Opening brace on a new line
method_brace_spacing_pattern = re.compile(r"\)\s*\{")  # Detects spacing issues between `()` and `{`

def get_java_files():
    """Recursively get all Java files in the src folder."""
    java_files = []
    for root, _, files in os.walk(SRC_FOLDER):
        for file in files:
            if file.endswith(".java"):
                java_files.append(os.path.join(root, file))
    return java_files

def check_unused_variables(files):
    print(f"{CYAN}Starting Unused Variable Check...{RESET}")
    violations = []

    for file_path in files:
        file_name = os.path.basename(file_path)
        declared_vars = {}
        used_vars = set()

        with open(file_path, "r", encoding="utf-8") as file:
            lines = file.readlines()

        for line_number, line in enumerate(lines, start=1):
            line = line.strip()

            # Ignore method parameter declarations
            if "(" in line and ")" in line:
                continue  # Skip method signatures

            # Detect variable declarations (handles "List test;" or "int x;")
            matches = variable_declaration_pattern.findall(line)
            for match in matches:
                declared_vars[match] = line_number  # Store the variable with its line number

            # Detect usage of declared variables
            for var in declared_vars.keys():
                if re.search(rf"\b{var}\b", line) and not re.match(rf"^\s*(?:int|double|float|long|short|char|boolean|String|List|ArrayList)\s+{var}\b", line):
                    used_vars.add(var)

        # Identify unused variables
        for var, line_number in declared_vars.items():
            if var not in used_vars:
                violations.append(f"{RED}{file_name}:{line_number}: Unused variable '{var}' detected.{RESET}")

    if violations:
        for violation in violations:
            print(violation)
    else:
        print(f"{GREEN}All files are clean for Unused Variable Check.{RESET}")

    return violations

def check_brace_placement(files):
    print(f"{CYAN}Starting Opening Brace Check...{RESET}")
    violations = []

    for file_path in files:
        file_name = os.path.basename(file_path)

        with open(file_path, "r", encoding="utf-8") as file:
            lines = file.readlines()

        for line_number, line in enumerate(lines, start=1):
            line = line.strip()

            # Detect incorrect opening brace position
            if brace_violation_pattern.match(line) and line_number > 1:
                previous_line = lines[line_number - 2].strip() if line_number > 1 else ""
                if previous_line and not previous_line.endswith("{"):
                    violations.append(f"{RED}{file_name}:{line_number}: Opening brace should not be on a new line.{RESET}")

    if violations:
        for violation in violations:
            print(violation)
    else:
        print(f"{GREEN}All files are clean for Opening Brace Check.{RESET}")

    return violations

def check_newline_at_end(files):
    print(f"{CYAN}Starting End-of-File Newline Check...{RESET}")
    violations = []

    for file_path in files:
        file_name = os.path.basename(file_path)

        with open(file_path, "r", encoding="utf-8") as file:
            content = file.read()

        # Strip ONLY carriage returns & excess newlines
        cleaned_content = content.rstrip("\r\n")

        # Ensure the file ends with EXACTLY one newline
        if cleaned_content + "\n" != content:
            violations.append(f"{RED}{file_name}: Missing newline at the end of the file.{RESET}")

    if violations:
        for violation in violations:
            print(violation)
    else:
        print(f"{GREEN}All files are clean for End-of-File Newline Check.{RESET}")

    return violations

def check_method_brace_spacing(files):
    print(f"{CYAN}Starting Method Brace Spacing Check...{RESET}")
    violations = []

    for file_path in files:
        file_name = os.path.basename(file_path)

        with open(file_path, "r", encoding="utf-8") as file:
            lines = file.readlines()

        for line_number, line in enumerate(lines, start=1):
            line = line.strip()

            # Detect incorrect spacing between `()` and `{`
            if method_brace_spacing_pattern.search(line):
                if not re.search(r"\)\s\{", line):  # Ensure there is exactly one space
                    violations.append(f"{RED}{file_name}:{line_number}: Incorrect spacing between `()` and `{{` in method definition.{RESET}")

    if violations:
        for violation in violations:
            print(violation)
    else:
        print(f"{GREEN}All files are clean for Method Brace Spacing Check.{RESET}")

    return violations

if __name__ == "__main__":
    java_files = get_java_files()

    total_violations = []
    total_violations.extend(check_unused_variables(java_files))
    print("------")
    total_violations.extend(check_brace_placement(java_files))
    print("------")
    total_violations.extend(check_newline_at_end(java_files))
    print("------")
    total_violations.extend(check_method_brace_spacing(java_files))
    print("------")

    if total_violations:
        sys.exit(1)  # Exit with error code to fail GitHub Actions
    else:
        sys.exit(0)  # No violations found
