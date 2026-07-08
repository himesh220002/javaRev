import re

with open("Regex.html", "r") as f:
    content = f.read()

# Isolate the section between lines 208 and 388
lines = content.splitlines()
prefix = "\n".join(lines[:207])
target = "\n".join(lines[207:388])
suffix = "\n".join(lines[388:])

# Replace table tags
target = re.sub(r'<table[^>]*>', '<table class="min-w-full text-left text-sm whitespace-nowrap border-collapse border border-gray-300 shadow-sm rounded-lg overflow-hidden">', target)
target = re.sub(r'<tr>', '<tr class="hover:bg-gray-50 transition-colors">', target)
target = re.sub(r'<th>', '<th class="px-4 py-3 bg-gray-100 font-semibold text-gray-700 border border-gray-300 uppercase tracking-wide text-xs">', target)
target = re.sub(r'<td>', '<td class="px-4 py-3 border border-gray-200 text-gray-700 font-mono text-sm">', target)

new_content = prefix + "\n" + target + "\n" + suffix

with open("Regex.html", "w") as f:
    f.write(new_content)

print("Tables updated.")
