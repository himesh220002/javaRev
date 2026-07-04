import os
import re

def update_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    original = content

    # Fix tables
    content = content.replace('class="bg-gray-800"', 'class="bg-gray-200 text-gray-900"')
    content = content.replace('tr class="bg-gray-800"', 'tr class="bg-gray-200 text-gray-900"')
    content = content.replace('border-gray-700', 'border-gray-300')
    
    # Fix code texts
    content = content.replace('text-green-300', 'text-green-700')
    content = content.replace('text-gray-300', 'text-gray-600')
    content = content.replace('text-gray-400', 'text-gray-500')
    
    # Fix inline code blocks and other divs
    content = content.replace('bg-gray-800', 'bg-gray-100 text-gray-900 border border-gray-200')
    content = content.replace('bg-gray-700', 'bg-gray-50 text-gray-900 border border-gray-200')
    
    # Fix outer divs that were missed
    content = re.sub(r'bg-gray-900\s+text-white', 'bg-gray-50 text-gray-800 border border-gray-200', content)

    # Some elements might now have redundant classes like `text-gray-900 text-gray-900` or `border border-gray-200 border border-gray-200`
    # Let's just do a quick deduplication of classes inside class="..."
    def deduplicate_classes(match):
        classes = match.group(1).split()
        seen = set()
        deduped = []
        for c in classes:
            if c not in seen:
                seen.add(c)
                deduped.append(c)
        return 'class="' + ' '.join(deduped) + '"'

    content = re.sub(r'class="([^"]+)"', deduplicate_classes, content)

    if original != content:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Updated: {filepath}")

def main():
    chapters_dir = 'chapters'
    for root, dirs, files in os.walk(chapters_dir):
        for file in files:
            if file.endswith('.html'):
                update_file(os.path.join(root, file))

if __name__ == '__main__':
    main()
