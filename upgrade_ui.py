import os
import glob
import re

html_files = glob.glob('chapters/*/*.html')

new_style_block = '''<script src="https://cdn.tailwindcss.com?plugins=typography"></script>
    <script>
        tailwind.config = {
            darkMode: 'class',
            theme: {
                extend: {
                    colors: {
                        slate: {
                            850: '#151e2e',
                            900: '#0f172a',
                            950: '#020617',
                        }
                    }
                }
            }
        }
    </script>
    <style>
        body {
            background-color: #020617;
            color: #f8fafc;
            margin: 0;
            display: flex;
            height: 100vh;
            overflow: hidden;
            font-family: 'Inter', sans-serif;
        }
        
        ::-webkit-scrollbar { width: 8px; height: 8px; }
        ::-webkit-scrollbar-track { background: #0f172a; }
        ::-webkit-scrollbar-thumb { background: #334155; border-radius: 4px; }
        ::-webkit-scrollbar-thumb:hover { background: #475569; }

        .mermaid {
            background: rgba(255,255,255,0.02);
            padding: 20px;
            border-radius: 12px;
            border: 1px solid #334155;
            display: flex;
            justify-content: center;
            margin-top: 2rem;
            margin-bottom: 2rem;
            overflow: auto;
        }
    </style>'''

for file_path in html_files:
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Clean up the previous tailwind script if it exists (like in Basics.html)
    content = content.replace('<script src="https://cdn.tailwindcss.com"></script>\n', '')
    content = content.replace('<script src="https://cdn.tailwindcss.com"></script>', '')
    
    # Replace <style>...</style> block
    style_pattern = re.compile(r'<style>.*?</style>', re.DOTALL)
    content = style_pattern.sub(new_style_block, content)
    
    # Modify main-content div if not already modified
    if 'prose-invert' not in content:
        content = content.replace('<div class="main-content">', 
                                '<div class="flex-1 p-8 md:p-14 overflow-y-auto bg-slate-950 prose prose-invert prose-indigo prose-headings:text-slate-100 max-w-none prose-a:text-blue-400 prose-pre:bg-slate-900 prose-pre:border prose-pre:border-slate-800">')
    
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)

print(f"Successfully upgraded UI for {len(html_files)} chapters!")
