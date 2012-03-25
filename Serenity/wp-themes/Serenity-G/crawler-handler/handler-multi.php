<!DOCTYPE html>
<html>
    <head>
        <meta content="<?php bloginfo('html_type'); ?>; charset=<?php bloginfo('charset'); ?>" http-equiv="content-type" >
        <?php include (TEMPLATEPATH . '/site-verify-code.php'); ?>
        <title>Serenity | Xi CHEN's blog</title>
        <link href="<?php bloginfo('url'); ?>/xmlrpc.php?rsd" title="RSD" type="application/rsd+xml" rel="EditURI">
        <link href="<?php bloginfo('url'); ?>/wp-includes/wlwmanifest.xml" type="application/wlwmanifest+xml" rel="wlwmanifest">
    </head>
    <body>
        <header id="title" style="display:block;">
            <h1>
                <a href="<?php bloginfo('url'); ?>" rel="bookmark" title="Serenity Home">
                    Serenity
                </a>
            </h1>
            <h2>- 
                <a href="<?php echo $page_url; ?>" >
                    <?php echo $handled_title; ?>
                </a>
            </h2>
        </header>

        <section>
        <?php 
            // The Loop
            $base_url = $base_url.'/article/';
            while ( $the_query->have_posts() ) : $the_query->the_post() ; $i_slug = $post->post_name;
        ?>
            <article id="<?php the_ID(); ?>" title="<?php the_title(); ?>">
                <header id="article-title">
                    <h2>
                        <a href="<?php echo $base_url.$i_slug; ?>" rel="bookmark" title="Permanent Link to <?php the_title_attribute(); ?>">
                            <?php the_title(); ?>
                        </a>
                    </h2>
                </header>
                <div id="article-content">
                    <?php the_excerpt(); ?>
                </div>
                <footer id="article-footer">
                    <?php the_tags(); ?>
                </footer>
            </article>
        <?php 
            endwhile; 
            wp_reset_postdata();
        ?>
        </section>

        <aside id="sidebar">
            <nav id="menu">
                <ul style="list-style:none;">
                    <li class="item">Home</li>
                    <li class="item">About</li>
                </ul>
            </nav>
        </aside>

        <footer>
            <?php wp_footer(); ?>
        </footer>
	</body>
</html>