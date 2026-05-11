#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  run.sh  —  Build & run the Online Examination System
# ─────────────────────────────────────────────────────────────

set -e  # Exit immediately on error

SRC_DIR="src"
BIN_DIR="bin"
MAIN_CLASS="com.exam.Main"

echo ""
echo "  ╔══════════════════════════════════════════════════════════╗"
echo "  ║         ONLINE EXAMINATION SYSTEM  v1.0                 ║"
echo "  ╚══════════════════════════════════════════════════════════╝"
echo ""

# Ensure bin/ directory exists
mkdir -p "$BIN_DIR"

echo "  [1/2]  Compiling Java sources..."

# Find and compile all .java files
find "$SRC_DIR" -name "*.java" > sources.txt
javac -d "$BIN_DIR" @sources.txt
rm -f sources.txt

echo "  [2/2]  Compilation successful. Starting application..."
echo ""

# Run the application
java -cp "$BIN_DIR" "$MAIN_CLASS"
