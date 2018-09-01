import tempfile

import PIL
import requests
from PIL import Image
from django.core import files
from django.core.files import images


def download_image(reddit_article):
    request = requests.get(reddit_article.article_top_image_url, stream=True)

    if request.status_code != requests.codes.ok:
        return

    temp = tempfile.NamedTemporaryFile()
    for block in request.iter_content(1024 * 8):
        if not block:
            break
        temp.write(block)

    image = files.File(temp)
    image.name = reddit_article.reddit_id + '.jpg'

    return image


def compress_image(file_path):
    final_width = 600
    image = Image.open(open(file_path, 'rb'))
    if image.size[0] > final_width:
        ratio = (final_width / float(image.size[0]))
        height = int((float(image.size[1]) * float(ratio)))
        image = image.resize((final_width, height), PIL.Image.ANTIALIAS)
    image.save(file_path, "JPEG", quality=70)
