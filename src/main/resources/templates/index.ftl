<#import "/layout.ftl" as layout>

<@layout.content>
<h2>Последние посты</h2>

<#if posts?has_content>
    <#list posts as p>
    <div class="post-card">
        <h2><a href="/post/${p.slug()}">${p.title()}</a></h2>
        <div class="meta">${p.publishedAt()!""}</div>
        <div>${p.excerpt()}</div>
    </div>
    </#list>
    <#else>
    <p>Нет опубликованных постов.</p>
</#if>
</@layout.content>
