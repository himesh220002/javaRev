import { defaultState } from '../../data.js';

export function initSidebar() {
    // Determine active module from URL
    const pathParts = window.location.pathname.split('/');
    const htmlFile = pathParts[pathParts.length - 1];
    const activeModName = htmlFile.replace('.html', '');

    const state = JSON.parse(localStorage.getItem('learningTrackerState_v3')) || defaultState;
    const modules_dict = state.Java.children;
    const module_names = Object.keys(modules_dict);


    let sidebarHtml = `
        <div class="w-[350px] min-w-[5vw] max-w-[50vw] bg-slate-900/95 backdrop-blur flex flex-col overflow-y-auto shrink-0 border-r border-slate-800" id="sidebar">
            <div class="p-6 border-b border-slate-800/50 sticky top-0 bg-slate-900/90 z-20 backdrop-blur-md">
                <a href="../../javaLearningContext.html" class="text-blue-400 hover:text-blue-300 transition-colors font-semibold text-sm flex items-center gap-2">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path></svg>
                    Back to Tracker
                </a>
            </div>
            <div class="flex-1 py-2">
    `;

    module_names.forEach((mod, index) => {
        const idx = index + 1;
        const cleanName = mod.replace(/[^a-zA-Z0-9]/g, '');
        const href = `../${idx}_${cleanName}/${cleanName}.html`;

        const isActive = (cleanName === activeModName);
        const activeClass = isActive ? 'border-l-4 border-blue-500 bg-white/5 text-blue-400 font-semibold' : 'text-slate-300 hover:bg-white/5 hover:text-white border-l-4 border-transparent';
        const subtopicsClass = isActive ? 'block' : 'hidden';

        let subtopicsHtml = '';
        const grandchildren = modules_dict[mod].grandchildren;
        for (const grand in grandchildren) {
            subtopicsHtml += `<div class="relative pl-4 py-1.5 text-sm text-slate-400 hover:text-slate-200 transition-colors before:content-['•'] before:absolute before:left-0 before:text-slate-600">${grand}</div>\n`;
        }

        sidebarHtml += `
            <div class="border-b border-slate-800/50 last:border-0">
                <div class="flex w-full group">
                    <a href="${href}" class="p-4 flex-1 transition-all duration-200 ${activeClass}">${mod}</a>
                    <button class="px-4 text-slate-500 hover:text-white hover:bg-white/5 transition-colors flex items-center" onclick="toggleSubtopics('sub_${idx}')">
                        <svg class="w-4 h-4 transition-transform duration-200 ${isActive ? 'rotate-180' : ''}" id="icon_${idx}" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path></svg>
                    </button>
                </div>
                <div class="${subtopicsClass} bg-black/20 px-4 py-3 pb-4" id="sub_${idx}">
                    ${subtopicsHtml}
                </div>
            </div>
        `;
    });

    sidebarHtml += `</div></div><div class="w-[5px] bg-slate-800 hover:bg-blue-500 cursor-col-resize shrink-0 transition-colors z-10" id="dragMe"></div>`;

    // Inject into container
    document.getElementById('sidebar-container').innerHTML = sidebarHtml;

    // Attach toggle function to window so inline onclick works
    window.toggleSubtopics = function (id) {
        const el = document.getElementById(id);
        const iconId = id.replace('sub_', 'icon_');
        const icon = document.getElementById(iconId);

        if (el) {
            if (el.classList.contains('hidden')) {
                el.classList.remove('hidden');
                el.classList.add('block');
                if (icon) icon.classList.add('rotate-180');
            } else {
                el.classList.remove('block');
                el.classList.add('hidden');
                if (icon) icon.classList.remove('rotate-180');
            }
        }
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
