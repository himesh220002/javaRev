import os

def update_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    original = content

    # 1. Body styles
    content = content.replace(
        "background-color: #020617;\n            color: #f8fafc;",
        "background-color: #f9fafb;\n            color: #1f2937;"
    )

    # 2. Main wrapper
    content = content.replace(
        'class="flex-1 p-8 md:p-14 overflow-y-auto bg-slate-950 prose prose-invert prose-indigo prose-headings:text-slate-100 max-w-none prose-a:text-blue-400 prose-pre:bg-slate-900 prose-pre:border prose-pre:border-slate-800"',
        'class="flex-1 p-8 md:p-14 overflow-y-auto bg-gray-50 text-gray-800 prose prose-indigo prose-headings:text-gray-900 max-w-none prose-a:text-blue-600 prose-pre:bg-gray-100 prose-pre:text-gray-900 prose-pre:border prose-pre:border-gray-200"'
    )

    # 3. Inner blocks
    content = content.replace(
        'class="bg-gray-900 text-white p-6 rounded-lg space-y-6"',
        'class="bg-gray-50 text-gray-800 p-6 rounded-lg space-y-6 border border-gray-200"'
    )

    # 4. Pre code blocks
    content = content.replace(
        'class="bg-gray-800 p-3 rounded"',
        'class="bg-gray-100 text-gray-900 p-3 rounded border border-gray-200"'
    )

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
