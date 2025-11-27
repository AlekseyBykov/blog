<#ftl output_format="HTML">

<#import "/layout.ftl" as layout>

<@layout.content>
    <article>
        <h2>${post.title()}</h2>
        <p class="meta">Published: ${post.publishedAt()}</p>

        <div style="margin-top:1.5rem; line-height:1.7;">
            ${post.contentHtml()?no_esc}
        </div>

        <p style="margin-top:2rem;">
            <a href="/">Back to the list</a>
        </p>

    </article>
</@layout.content>
