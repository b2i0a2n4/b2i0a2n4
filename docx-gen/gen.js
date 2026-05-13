const fs = require('fs');
const docx = require('docx');
const { Document, Packer, Paragraph, TextRun, HeadingLevel } = docx;

const doc = new Document({
    creator: "Antigravity",
    title: "公寓管理系统毕业论文大纲",
    description: "毕业论文大纲",
    sections: [
        {
            properties: {},
            children: [
                new Paragraph({
                    text: "公寓管理系统毕业论文大纲",
                    heading: HeadingLevel.HEADING_1,
                    alignment: docx.AlignmentType.CENTER,
                }),
                new Paragraph({ text: "第一章 绪论", heading: HeadingLevel.HEADING_2 }),
                new Paragraph({ text: "1.1 研究背景与意义", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "研究背景：", bold: true }),
                        new TextRun("阐述当前高校或企业公寓管理的现状，随着公寓规模扩大，传统的人工管理或老旧系统暴露出效率低下、信息容易出错、难以进行数据统计等问题。")
                    ]
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "研究意义：", bold: true }),
                        new TextRun("说明开发基于现代Web技术的公寓管理系统的价值。例如：提高管理效率、实现信息化和无纸化办公、方便公寓管理员与住户的信息交互等。")
                    ]
                }),
                new Paragraph({ text: "1.2 问题及研究现状", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "存在的问题：", bold: true }),
                        new TextRun("指出传统管理模式在入住办理、退宿、费用统计、设备报修等环节的痛点。")
                    ]
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "研究现状：", bold: true }),
                        new TextRun("分析目前市面上已有的管理系统（国内外现状），指出它们可能存在的技术老旧（如还在使用JSP）、交互体验差、或者功能不符合特定场景需求等局限性。")
                    ]
                }),
                new Paragraph({ text: "1.3 主要研究内容", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "简述本论文要完成的核心任务：设计并实现一个前后端分离的公寓管理系统。", bullet: { level: 0 } }),
                new Paragraph({ text: "概括系统主要功能模块：如房间管理、入住/退宿管理、用户权限管理等。", bullet: { level: 0 } }),
                new Paragraph({ text: "说明论文的结构安排（第一章做什么，第二章做什么……）。", bullet: { level: 0 } }),

                new Paragraph({ text: "第二章 相关技术介绍", heading: HeadingLevel.HEADING_2 }),
                new Paragraph({ text: "2.1 后端技术", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "Java语言：", bold: true }),
                        new TextRun("作为系统的核心开发语言，具有跨平台和面向对象的优势（本项目使用 JDK 17）。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "Spring Boot 框架：", bold: true }),
                        new TextRun("简化了Spring应用的初始搭建以及开发过程（本项目采用 Spring Boot 4.x）。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "MyBatis 框架：", bold: true }),
                        new TextRun("介绍用于持久层的数据持久化技术及其优势。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "MySQL 数据库：", bold: true }),
                        new TextRun("介绍系统的数据存储方案。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "Redis：", bold: true }),
                        new TextRun("介绍使用Redis进行数据缓存或会话管理。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "JWT (JSON Web Token)：", bold: true }),
                        new TextRun("介绍用于系统前后端分离架构下的身份认证机制。")
                    ], bullet: { level: 0 }
                }),

                new Paragraph({ text: "2.2 前端技术", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "Vue 3：", bold: true }),
                        new TextRun("介绍现代化的前端渐进式框架，其响应式原理和组合式API (Composition API) 的优势。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "Element Plus：", bold: true }),
                        new TextRun("介绍基于Vue 3的UI组件库，用于快速构建美观的后台管理界面。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "Vite：", bold: true }),
                        new TextRun("介绍下一代前端构建工具，强调其冷启动快和热更新迅速的特点。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "Pinia & Vue Router：", bold: true }),
                        new TextRun("介绍状态管理和前端路由控制。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "Axios：", bold: true }),
                        new TextRun("介绍用于前后端数据交互的HTTP请求库。")
                    ], bullet: { level: 0 }
                }),

                new Paragraph({ text: "2.3 开发工具", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "介绍开发过程中使用的IDE（如 IntelliJ IDEA, VS Code）。", bullet: { level: 0 } }),
                new Paragraph({ text: "介绍项目构建和依赖管理工具（Maven, npm）。", bullet: { level: 0 } }),
                new Paragraph({ text: "介绍版本控制工具（Git）和API测试工具（Postman/Apifox等）。", bullet: { level: 0 } }),

                new Paragraph({ text: "2.4 本章小结", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "总结本章内容，说明所选技术栈的前瞻性与合理性，为后续的系统设计与实现奠定技术基础。", bullet: { level: 0 } }),

                new Paragraph({ text: "第三章 系统分析", heading: HeadingLevel.HEADING_2 }),
                new Paragraph({ text: "3.1 可行性研究", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "技术可行性：", bold: true }),
                        new TextRun("分析所选用的Vue 3 + Spring Boot技术栈是否成熟，开发团队（或个人）是否具备相应能力。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "经济可行性：", bold: true }),
                        new TextRun("分析开发成本（开源免费框架为主）与系统投入使用后带来的效益（节约人力成本）。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "操作可行性：", bold: true }),
                        new TextRun("分析系统界面设计的易用性，公寓管理人员能否快速上手。")
                    ], bullet: { level: 0 }
                }),

                new Paragraph({ text: "3.2 需求分析", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "功能需求分析：", bold: true }),
                        new TextRun("详细描述系统的核心业务流程和用例（Use Case）。例如：管理员端包含楼宇管理、房间管理、入住办理等；住户端包含信息查询、报修申请等。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "非功能需求分析：", bold: true }),
                        new TextRun("系统的性能要求、安全性要求（如密码加密、JWT拦截）、可靠性等。")
                    ], bullet: { level: 0 }
                }),

                new Paragraph({ text: "3.3 本章小结", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "总结系统分析阶段的成果，明确系统“需要做什么”。", bullet: { level: 0 } }),

                new Paragraph({ text: "第四章 系统设计", heading: HeadingLevel.HEADING_2 }),
                new Paragraph({ text: "4.1 系统总体设计", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "系统架构设计：", bold: true }),
                        new TextRun("描述 B/S 架构和前后端分离模式，展示前端 Vue 与后端 Spring Boot 的交互流程。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "功能模块设计：", bold: true }),
                        new TextRun("给出系统的功能结构图，将大系统拆分为子模块（如用户模块、房间模块、入住模块等）。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "数据库设计：", bold: true }),
                        new TextRun("分为概念结构设计 (E-R图) 和逻辑结构设计 (数据表设计)。")
                    ], bullet: { level: 0 }
                }),

                new Paragraph({ text: "4.2 本章小结", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "总结设计阶段的核心产出，为第五章的代码编写提供蓝图。", bullet: { level: 0 } }),

                new Paragraph({ text: "第五章 系统实现", heading: HeadingLevel.HEADING_2 }),
                new Paragraph({ text: "5.1 开发环境搭建", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "描述如何配置 JDK 17, Node.js, MySQL 以及运行 Redis 的环境准备工作，简述项目初始化过程。", bullet: { level: 0 } }),

                new Paragraph({ text: "5.2 后端核心功能实现", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "展示后端分层架构（Controller, Service, Mapper）的实现逻辑。", bullet: { level: 0 } }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "安全认证实现：", bold: true }),
                        new TextRun("展示如何使用 JWT 和拦截器进行登录校验和权限拦截。")
                    ], bullet: { level: 0 }
                }),
                new Paragraph({
                    children: [
                        new TextRun({ text: "核心业务代码展示：", bold: true }),
                        new TextRun("挑选有代表性的复杂业务逻辑进行代码分析，如分配床位时动态更新房间满员状态的逻辑。")
                    ], bullet: { level: 0 }
                }),

                new Paragraph({ text: "5.3 前端核心功能实现", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "前端路由与权限拦截：展示 Vue Router 的配置以及如何根据登录状态拦截非法访问。", bullet: { level: 0 } }),
                new Paragraph({ text: "页面布局与组件化：展示如何利用 Element Plus 搭建页面布局。", bullet: { level: 0 } }),
                new Paragraph({ text: "数据交互：展示 Axios 请求封装以及如何处理后端返回的数据并渲染到表格或表单中。", bullet: { level: 0 } }),

                new Paragraph({ text: "5.4 本章小结", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "总结系统编码实现的过程，展示系统的主要运行截图。", bullet: { level: 0 } }),

                new Paragraph({ text: "第六章 系统测试", heading: HeadingLevel.HEADING_2 }),
                new Paragraph({ text: "6.1 测试目的和方法", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "测试目的：发现系统中存在的Bug，验证系统功能是否满足需求分析。", bullet: { level: 0 } }),
                new Paragraph({ text: "测试方法：主要采用黑盒测试（功能测试），结合部分白盒测试（单元测试）。", bullet: { level: 0 } }),

                new Paragraph({ text: "6.2 测试结果", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "以表格的形式列出核心功能的测试用例（如登录测试用例、添加房间测试用例、入住逻辑测试用例），并附带预期结果和实际运行结果。", bullet: { level: 0 } }),

                new Paragraph({ text: "6.3 本章小结", heading: HeadingLevel.HEADING_3 }),
                new Paragraph({ text: "对测试阶段进行总结，说明系统经过测试后运行稳定，达到了预期目标。", bullet: { level: 0 } }),

                new Paragraph({ text: "结论", heading: HeadingLevel.HEADING_2 }),
                new Paragraph({ text: "总结整个毕业设计的开发过程，重申系统实现的价值，并客观分析系统目前存在的不足以及未来的改进方向。", bullet: { level: 0 } }),

                new Paragraph({ text: "致谢", heading: HeadingLevel.HEADING_2 }),
                new Paragraph({ text: "感谢指导老师的指导、同学的帮助以及开源社区提供的技术支持。", bullet: { level: 0 } }),

                new Paragraph({ text: "参考文献", heading: HeadingLevel.HEADING_2 }),
                new Paragraph({ text: "列出在编写论文和开发系统过程中参考的书籍、学术论文、技术文档等。", bullet: { level: 0 } })
            ],
        },
    ],
});

Packer.toBuffer(doc).then((buffer) => {
    fs.writeFileSync("D:\\AntigravityProjects\\公寓管理系统毕业论文大纲.docx", buffer);
    console.log("Document created successfully");
});
