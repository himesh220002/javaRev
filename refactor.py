import json
import os

with open('javaLearningContext.html', 'r') as f:
    content = f.read()

start_str = 'const defaultState = '
end_str = '};\n\n        let state ='
start_idx = content.find(start_str)
end_idx = content.find(end_str, start_idx)

state_string = content[start_idx + len(start_str):end_idx + 1]
state = json.loads(state_string)

with open('data.js', 'w') as f:
    f.write('export const defaultState = ' + state_string + ';\n')

sidebar_js = '''import { defaultState } from '../../data.js';

export function initSidebar() {
    // Determine active module from URL
    const pathParts = window.location.pathname.split('/');
    const htmlFile = pathParts[pathParts.length - 1];
    const activeModName = htmlFile.replace('.html', '');

    const state = JSON.parse(localStorage.getItem('learningTrackerState_v3')) || defaultState;
    const modules_dict = state.Java.children;
    const module_names = Object.keys(modules_dict);

    let sidebarHtml = `
        <div class="sidebar" id="sidebar">
            <div class="sidebar-header">
                <a href="../../javaLearningContext.html">← Back to Tracker</a>
            </div>
    `;

    module_names.forEach((mod, index) => {
        const idx = index + 1;
        const cleanName = mod.replace(/[^a-zA-Z0-9]/g, '');
        const href = `../${idx}_${cleanName}/${cleanName}.html`;
        
        const isActive = (cleanName === activeModName);
        const activeClass = isActive ? 'active' : '';
        const subtopicsClass = isActive ? 'open' : '';

        let subtopicsHtml = '';
        const grandchildren = modules_dict[mod].grandchildren;
        for (const grand in grandchildren) {
            subtopicsHtml += `<div class="subtopic-item">${grand}</div>\\n`;
        }

        sidebarHtml += `
            <div class="nav-item">
                <div class="nav-module-container">
                    <a href="${href}" class="nav-module ${activeClass}">${mod}</a>
                    <div class="dropdown-toggle" onclick="toggleSubtopics('sub_${idx}')">▼</div>
                </div>
                <div class="subtopics ${subtopicsClass}" id="sub_${idx}">
                    ${subtopicsHtml}
                </div>
            </div>
        `;
    });

    sidebarHtml += `</div><div class="resizer" id="dragMe"></div>`;
    
    // Inject into container
    document.getElementById('sidebar-container').innerHTML = sidebarHtml;

    // Attach toggle function to window so inline onclick works
    window.toggleSubtopics = function(id) {
        const el = document.getElementById(id);
        if (el) el.classList.toggle('open');
    };

    // Attach resizer logic
    const sidebarEl = document.getElementById('sidebar');
    const resizer = document.getElementById('dragMe');
    let isResizing = false;

    resizer.addEventListener('mousedown', (e) => {
        isResizing = true;
        resizer.classList.add('resizing');
        document.body.style.cursor = 'col-resize';
        e.preventDefault();
    });

    document.addEventListener('mousemove', (e) => {
        if (!isResizing) return;
        let newWidth = e.clientX;
        const minWidth = window.innerWidth * 0.05;
        const maxWidth = window.innerWidth * 0.50;
        if (newWidth < minWidth) newWidth = minWidth;
        if (newWidth > maxWidth) newWidth = maxWidth;
        sidebarEl.style.width = newWidth + 'px';
    });

    document.addEventListener('mouseup', () => {
        if (isResizing) {
            isResizing = false;
            resizer.classList.remove('resizing');
            document.body.style.cursor = 'default';
        }
    });
}
'''

with open('sidebar.js', 'w') as f:
    f.write(sidebar_js)

# Update javaLearningContext.html to use data.js
new_content = content.replace(
    'const defaultState = ' + state_string + ';',
    "import { defaultState } from './data.js';"
)
# Note: Since javaLearningContext.html is a module script, we also need to move the import to the top of the script tag,
# but javascript module imports must be top-level. Currently the <script type="module"> block has `import mermaid` at the top.
# Let's do a more robust string replacement for javaLearningContext.html.

import_str = "import { defaultState } from './data.js';\n"
if "import mermaid from" in new_content:
    new_content = new_content.replace(
        "import mermaid from 'https://cdn.jsdelivr.net/npm/mermaid@10/dist/mermaid.esm.min.mjs';",
        "import mermaid from 'https://cdn.jsdelivr.net/npm/mermaid@10/dist/mermaid.esm.min.mjs';\n        " + import_str
    )
    new_content = new_content.replace("import { defaultState } from './data.js';", "", 1) # remove the one in the middle

with open('javaLearningContext.html', 'w') as f:
    f.write(new_content)

# Update all 17 chapter HTML files
import glob

html_files = glob.glob('chapters/*/*.html')
for file_path in html_files:
    with open(file_path, 'r') as f:
        html = f.read()
    
    # We want to replace everything from <div class="sidebar" id="sidebar"> to <div class="main-content">
    # with `<div id="sidebar-container" style="display:flex;"></div>\n    <div class="main-content">`
    
    s_idx = html.find('<div class="sidebar" id="sidebar">')
    e_idx = html.find('<div class="main-content">')
    
    if s_idx != -1 and e_idx != -1:
        prefix = html[:s_idx]
        suffix = html[e_idx:]
        
        # Inject our container and module script just before the end of the body
        new_html = prefix + '<div id="sidebar-container" style="display:flex; height:100%;"></div>\n    ' + suffix
        
        # Add the script tag to call initSidebar()
        script_tag = '''
    <script type="module">
        import { initSidebar } from '../../sidebar.js';
        initSidebar();
    </script>
</body>'''
        new_html = new_html.replace('</body>', script_tag)
        
        with open(file_path, 'w') as f:
            f.write(new_html)

print('Successfully extracted sidebar component and updated all files.')
